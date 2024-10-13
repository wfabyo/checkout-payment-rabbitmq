class PaymentService {
    constructor(channel) {
        this.channel = channel;
    }

    async processPayment(order) {
        // Simulação de processamento de pagamento
        const isSuccessful = Math.random() < 0.8; // 80% de chance de sucesso

        const result = {
            orderId: order.id,
            status: isSuccessful ? 'PAID' : 'CANCELLED'
        };

        // Enviar resultado de volta para o serviço de checkout
        this.channel.publish('payment-exchange', 'payment.result', Buffer.from(JSON.stringify(result)));

        console.log(`Payment ${isSuccessful ? 'successful' : 'failed'} for order ${order.id}`);
    }
}

module.exports = PaymentService;