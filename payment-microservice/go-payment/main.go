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
	instance := eureka.NewInstanceInfo("localhost", "go-api", "192.168.8.100", 9090, 30, false) //Create a new instance to register
	err := c.RegisterInstance("go-api", instance)
	if err != nil {
		log.Println(err)
	}
	app, err := c.GetApplication("go-api")
	log.Println(app.Instances)
	if err != nil {
		log.Fatal(err)
	}
	err = c.SendHeartbeat("go-api", instance.HostName)
	if err != nil {
		log.Fatal(err)
	}
	basePath := server.Group("/v1")
	paypalController.RegisterRoutes(basePath)
	log.Fatalf(server.Run(":9090").Error())
}
