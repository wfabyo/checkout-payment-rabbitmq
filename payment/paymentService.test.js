const PaymentService = require('./paymentService');

describe('PaymentService', () => {
  let paymentService;
  let mockChannel;

  beforeEach(() => {
    mockChannel = {
      publish: jest.fn()
    };
    paymentService = new PaymentService(mockChannel);
  });

  test('processPaymentWithCard should validate card and process payment', async () => {
    const orderId = '12345';
    const cardInfo = {
      cardNumber: '1234567890123456',
      expiryDate: '12/25',
      cvv: '123'
    };

    const result = await paymentService.processPaymentWithCard(orderId, cardInfo);

    expect(result).toHaveProperty('orderId', orderId);
    expect(result).toHaveProperty('status');
    expect(['PAID', 'CANCELLED']).toContain(result.status);
    expect(mockChannel.publish).toHaveBeenCalled();
  });

  test('processPaymentWithCard should throw error for invalid card', async () => {
    const orderId = '12345';
    const cardInfo = {
      cardNumber: 'invalid',
      expiryDate: 'invalid',
      cvv: 'invalid'
    };

    await expect(paymentService.processPaymentWithCard(orderId, cardInfo))
      .rejects.toThrow('Invalid card information');
  });

  test('validateCard should return true for valid card', () => {
    const cardInfo = {
      cardNumber: '1234567890123456',
      expiryDate: '12/25',
      cvv: '123'
    };

    expect(paymentService.validateCard(cardInfo)).toBe(true);
  });

  test('validateCard should return false for invalid card number', () => {
    const cardInfo = {
      cardNumber: '123', // Invalid number
      expiryDate: '12/25',
      cvv: '123'
    };

    expect(paymentService.validateCard(cardInfo)).toBe(false);
  });

  test('validateCard should return false for invalid expiry date', () => {
    const cardInfo = {
      cardNumber: '1234567890123456',
      expiryDate: '13/25', // Invalid month
      cvv: '123'
    };

    expect(paymentService.validateCard(cardInfo)).toBe(false);
  });

  test('validateCard should return false for invalid CVV', () => {
    const cardInfo = {
      cardNumber: '1234567890123456',
      expiryDate: '12/25',
      cvv: '12' // Invalid CVV
    };

    expect(paymentService.validateCard(cardInfo)).toBe(false);
  });
});