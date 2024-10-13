const express = require('express');
const amqp = require('amqplib');
const PaymentService = require('./paymentService');

const app = express();
app.use(express.json());

const RABBITMQ_URL = 'amqp://localhost'; // Ajuste conforme necessário

async function startServer() {
    const connection = await amqp.connect(RABBITMQ_URL);
    const channel = await connection.createChannel();

    await channel.assertQueue('payment-result-queue', { durable: true });
    await channel.assertExchange('payment-exchange', 'topic', { durable: true });

    const paymentService = new PaymentService(channel);

    channel.consume('payment-process-queue', async (msg) => {
        if (msg !== null) {
            const order = JSON.parse(msg.content.toString());
            await paymentService.processPayment(order);
            channel.ack(msg);
        }
    });

    app.listen(3000, () => console.log('Payment Gateway service running on port 3000'));
}

startServer().catch(console.error);