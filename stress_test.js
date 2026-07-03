import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 1,
  iterations: 1,
};

export default function () {
  const myDouble = 1.0;
  const url = `http://localhost:8080/api/accounts/1/withdraw?amount=${myDouble}`;

  const requests = [];
  for (let i = 0; i < 10; i++) {
    requests.push({ method: 'PUT', url: url, body: null });
  }

  console.log('Firing 10 simultaneous payment requests to test crash resiliency...');
  const responses = http.batch(requests);

  responses.forEach((res, index) => {
    console.log(`Req ${index + 1}: Status = ${res.status} | Body = ${res.body}`);

    check(res, {
      'did NOT return a 500 Server Error': (r) => r.status < 500,
      'handled idempotency gracefully': (r) => r.status === 200 || r.status === 409 || r.status === 422,
    });
  });
}