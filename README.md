# Idempotent Payment Gateway Wrapper
This payment wrapper is an API layer that sits between an e-commerce ecosystem and an external third-party payment processor (Stripe) to guarantee financial safety. It is a protection from duplicate customer charges and absolute data drift. This project is currently in progress. I am building this to learn Distributed Systems, Java, Springboot, PostgreSQL, Database Concurrency & Isolation, and REST API systems.

## :world_map: Current Status & Goals
- [x] Postgres Database connection
- [x] Basic Financial Endpoints (Deposit/Withdrawal)
- [ ] First k6 Vulnerability Testing

## Idempotency logic
Check if Key exists in DB.
  If YES and Status is SUCCESS: Return cached response.
  If YES and Status is PENDING:
    - Has it been LESS than 30 seconds? -> Return 409 Conflict (Active in-flight).
    - Has it been MORE than 30 seconds? -> TIMEOUT! (Safe to retry).
  If NO (or status is TIMEOUT):
    1. Save/Update DB to PENDING (and update the timestamp to NOW).
    2. Make the call to Stripe (passing the key).
    3. Update DB to SUCCESS and return data.
