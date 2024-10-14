class PaymentService {
    constructor(channel) {
        this.channel = channel;
    }

    async processPaymentWithCard(orderId, cardInfo) {
        // Validação básica do cartão
        if (!this.validateCard(cardInfo)) {
            throw new Error('Invalid card information');
        }

        // Simulação de processamento de pagamento
        const isSuccessful = Math.random() < 0.8; // 80% de chance de sucesso

        const result = {
            orderId: orderId,
            status: isSuccessful ? 'PAID' : 'CANCELLED'
        };

        // Enviar resultado de volta para o serviço de checkout
        this.channel.publish('payment-exchange', 'payment.result', Buffer.from(JSON.stringify(result)));

        console.log(`Payment ${isSuccessful ? 'successful' : 'failed'} for order ${orderId}`);

        return result;
    }

    validateCard(cardInfo) {
        // Implementação simplificada de validação de cartão
        const { cardNumber, expiryDate, cvv } = cardInfo;
        
        // Verifica se o número do cartão tem 16 dígitos
        if (!/^\d{16}$/.test(cardNumber)) return false;
        
        // Verifica se a data de expiração está no formato MM/YY
        if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(expiryDate)) return false;
        
        // Verifica se o CVV tem 3 dígitos
        if (!/^\d{3}$/.test(cvv)) return false;

        return true;
    }
}

module.exports = PaymentService;