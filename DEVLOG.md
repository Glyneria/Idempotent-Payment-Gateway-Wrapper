# Idempotent Payment Gateway Wrapper Devlog
This is the main journal for the development of this project
### [2026.07.03] {setting up the happy path with k6 race condition vulnerability testing}
implement basic deposit and withdrawal endpoints
stress test using k6, race condition discovery
- conditions: 1 virtual users, 10 withdraw requests at the same milisecond by 1.0
- result: it incremented and decremented over the same amount: 23099.0 and 23098.0 which should've been 23090.0
- goal: with the idempotency fix, it would've been 23099.0 accepting only one request
```bash
INFO[0000] Firing 10 simultaneous payment requests to test crash resiliency...  source=console
INFO[0000] Req 1: Status = 200 | Body = {"ownerName":"Charles","accountNumber":"12345","balance":23099.0,"id":1}  source=console
INFO[0000] Req 2: Status = 200 | Body = {"ownerName":"Charles","accountNumber":"12345","balance":23099.0,"id":1}  source=console
INFO[0000] Req 3: Status = 200 | Body = {"ownerName":"Charles","accountNumber":"12345","balance":23098.0,"id":1}  source=console
INFO[0000] Req 4: Status = 200 | Body = {"ownerName":"Charles","accountNumber":"12345","balance":23098.0,"id":1}  source=console
INFO[0000] Req 5: Status = 200 | Body = {"ownerName":"Charles","accountNumber":"12345","balance":23099.0,"id":1}  source=console
INFO[0000] Req 6: Status = 200 | Body = {"ownerName":"Charles","accountNumber":"12345","balance":23099.0,"id":1}  source=console
INFO[0000] Req 7: Status = 200 | Body = {"ownerName":"Charles","accountNumber":"12345","balance":23099.0,"id":1}  source=console
INFO[0000] Req 8: Status = 200 | Body = {"ownerName":"Charles","accountNumber":"12345","balance":23099.0,"id":1}  source=console
INFO[0000] Req 9: Status = 200 | Body = {"ownerName":"Charles","accountNumber":"12345","balance":23098.0,"id":1}  source=console
INFO[0000] Req 10: Status = 200 | Body = {"ownerName":"Charles","accountNumber":"12345","balance":23098.0,"id":1}  source=console
```
### [2026.07.06] {create the idempotency logic and run the same k6 1stress_test.js}