package main

import (
	"context"
	"github.com/ArthurHlt/go-eureka-client/eureka"
	"github.com/gin-gonic/gin"
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
	ctx = context.TODO()
	server = gin.Default()
	client, err := paypalsdk.NewClient(os.Getenv("PAYPAL_CLIENT_ID"), os.Getenv("PAYPAL_CLIENT_SECRET"), paypalsdk.APIBaseSandBox)
	if err != nil {
		log.Println("can not resolve env file")
	}
	_, err = client.GetAccessToken()
	if err != nil {
		log.Println("can get access token")
	}

	paypalService = services.NewPaypalService(client, logger)
	paypalController = controllers.NewPaypalController(paypalService, logger)

}
func main() {
	c := eureka.NewClient([]string{
		"http://127.0.0.1:8761/eureka",
	})
	instance1 := eureka.NewInstanceInfo(" 172.20.10.4", "goapi", "192.168.8.100", 9090, 30, false) //Create a new instance1 to register
	err := c.RegisterInstance("goapi", instance1)
	if err != nil {
		log.Println(err)
	}
	err = c.SendHeartbeat(instance1.App, instance1.HostName)
	if err != nil {
		log.Fatal(err)
	}

	basePath := server.Group("/v1")
	paypalController.RegisterRoutes(basePath)
	log.Fatalf(server.Run(":9090").Error())
}
