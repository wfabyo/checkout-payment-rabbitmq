const express = require('express');
const swaggerUi = require('swagger-ui-express');
const swaggerJsdoc = require('swagger-jsdoc');
const PaymentService = require('./paymentService');

const app = express();
app.use(express.json());

const swaggerOptions = {
    definition: {
        openapi: '3.0.0',
        info: {
            title: 'Payment Gateway API',
            version: '1.0.0',
            description: 'API for processing payments',
        },
        servers: [
            {
                url: 'http://localhost:3000',
                description: 'Development server',
            },
        ],
    },
    apis: ['./server.js'],
};

const swaggerSpec = swaggerJsdoc(swaggerOptions);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

function setupRoutes(paymentService) {
  /**
     * @swagger
     * /api/process-payment:
     *   post:
     *     summary: Process a payment
     *     description: Process a payment with credit card information
     *     requestBody:
     *       required: true
     *       content:
     *         application/json:
     *           schema:
     *             type: object
     *             required:
     *               - orderId
     *               - cardInfo
     *             properties:
     *               orderId:
     *                 type: string
     *                 description: The ID of the order
     *               cardInfo:
     *                 type: object
     *                 required:
     *                   - cardNumber
     *                   - expiryDate
     *                   - cvv
     *                 properties:
     *                   cardNumber:
     *                     type: string
     *                     description: The credit card number
     *                   expiryDate:
     *                     type: string
     *                     description: The expiry date of the card (MM/YY)
     *                   cvv:
     *                     type: string
     *                     description: The CVV of the card
     *     responses:
     *       200:
     *         description: Payment processed successfully
     *         content:
     *           application/json:
     *             schema:
     *               type: object
     *               properties:
     *                 orderId:
     *                   type: string
     *                 status:
     *                   type: string
     *                   enum: [PAID, CANCELLED]
     *       400:
     *         description: Invalid input
     */
  app.post('/api/process-payment', async (req, res) => {
    const { orderId, cardInfo } = req.body;
    try {
      const result = await paymentService.processPaymentWithCard(orderId, cardInfo);
      res.json(result);
    } catch (error) {
      res.status(400).json({ error: error.message });
    }
  });
}

module.exports = { app, setupRoutes };