package main

import (
	"context"
	"github.com/ArthurHlt/go-eureka-client/eureka"
	"github.com/gin-gonic/gin"
	"github.com/joho/godotenv"
	"github.com/netlify/PayPal-Go-SDK"
	"kastouri/payment-api/controllers"
	"kastouri/payment-api/services"
	"log"
	"os"
)

var (
	server           *gin.Engine
	ctx              context.Context
	client           *paypalsdk.Client
	paypalController *controllers.Paypal
	paypalService    *services.Paypal
	logger           *log.Logger
)

func init() {
	logger = log.Default()
	godotenv.Load(".env")
	ctx = context.TODO()
	server = gin.Default()
	client, err := paypalsdk.NewClient(os.Getenv("PAYPAL_CLIENT_ID"), os.Getenv("PAYPAL_CLIENT_SECRET"), paypalsdk.APIBaseSandBox)
	if err != nil {
		log.Fatal(err)
	}
	_, err = client.GetAccessToken()
	if err != nil {
		log.Fatal(err)
	}
	paypalService = services.NewPaypalService(client, logger)
	paypalController = controllers.NewPaypalController(paypalService)

}
func main() {
	c := eureka.NewClient([]string{
		"http://127.0.0.1:8761/eureka",
	})
	instance := eureka.NewInstanceInfo("localhost", "go", "192.168.8.100", 9090, 30, false) //Create a new instance to register
	instance.Metadata = &eureka.MetaData{
		Map: make(map[string]string),
	}
	c.RegisterInstance("go", instance)             // Register new instance in your eureka(s)
	c.GetApplication(instance.App)                 // retrieve the application "test"
	c.GetInstance(instance.App, instance.HostName) // retrieve the instance from "test.com" inside "test"" app
	err := c.SendHeartbeat(instance.App, instance.HostName)
	if err != nil {
		log.Fatal(err)
	}
	basePath := server.Group("/v1")
	paypalController.RegisterRoutes(basePath)
	log.Fatalf(server.Run(":9090").Error())
}
