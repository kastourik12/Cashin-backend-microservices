package response

type Paypal struct {
	PaymentId   string `json:"paymentId"`
	UserId      string `json:"userId"`
	SuccessLink string `json:"successLink"`
}
