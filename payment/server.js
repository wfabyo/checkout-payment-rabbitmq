require('dotenv').config();
const amqp = require('amqplib');
const { app, setupRoutes } = require('./app');
const PaymentService = require('./paymentService');

const RABBITMQ_URL = process.env.RABBITMQ_URL || 'amqp://localhost';
const PORT = process.env.PORT || 3000;


async function startServer() {
    const connection = await amqp.connect(RABBITMQ_URL);
    const channel = await connection.createChannel();

    await channel.assertQueue('payment-result-queue', { durable: true });
    await channel.assertExchange('payment-exchange', 'topic', { durable: true });

    const paymentService = new PaymentService(channel);
    setupRoutes(paymentService);

    app.listen(PORT, () => console.log(`Payment Gateway service running on port ${PORT}`));
}

startServer().catch(console.error);