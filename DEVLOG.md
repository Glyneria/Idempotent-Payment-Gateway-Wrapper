# Idempotent Payment Gateway Wrapper Devlog
This is the main journal for the development of this project
### [2026.07.03] {setting up the happy path with k6 race condition vulnerability testing}
- implement basic deposit and withdrawal endpoints
- stress test using k6
    conditions: 1 virtual users, 10 withdraw requests at the same milisecond by 1.0
    result: it incremented and decremented over the same amount: 23099.0 and 23098.0 which should've been 23090.0
    goal: with the idempotency fix, it would've been 23099.0 accepting only one request