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
	accessToken, err := client.GetAccessToken()
	if err != nil {
		log.Fatal(err)
	}
	log.Println(accessToken.Token)
	paypalService = services.NewPaypalService(client, logger)
	paypalController = controllers.NewPaypalController(paypalService)

}
func main() {
	client := eureka.NewClient([]string{
		"http://127.0.0.1:8761/eureka", //From a spring boot based eureka server
		// add others servers here
	})
	instance := eureka.NewInstanceInfo("localhost", "go-paypal-api", "127.0.0.1", 9090, 30, false) //Create a new instance to register
	instance.Metadata = &eureka.MetaData{
		Map: make(map[string]string),
	}
	//add metadata for example
	client.RegisterInstance("go-paypal-api", instance) // Register new instance in your eureka(s) // Retrieves all applications from eureka server(s) // retrieve the instance from "test.com" inside "test"" app
	client.SendHeartbeat(instance.App, instance.HostName)
	basePath := server.Group("/v1")
	paypalController.RegisterRoutes(basePath)
	log.Fatalf(server.Run(":9090").Error())
}
