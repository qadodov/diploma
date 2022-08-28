package data;

import lombok.Data;

public class EntryHelper {

    @Data
    public class OrderEntity {
        private String id;
        private String created;
        private String credit_id;
        private String payment_id;
    }

    @Data
    public class PaymentEntity {
        private String id;
        private String amount;
        private String created;
        private String status;
        private String transaction_id;
    }

    @Data
    public class CreditRequestEntity {
        private String id;
        private String bank_id;
        private String created;
        private String status;
    }
}
