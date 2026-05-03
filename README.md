# SimpleStock

A simplified stock market simulation REST API built with Spring Boot, PostgreSQL, and Docker.

## Architecture

```
Client → Nginx (load balancer) → app1 ┐
                                       ├→ PostgreSQL
                                  app2 ┘
```

- **2 application instances** behind an Nginx load balancer — killing one instance does not bring the service down
- **PostgreSQL** as shared persistent store with Flyway migrations
- **Nginx** handles round-robin load balancing

## Requirements

- Docker with Docker Compose
- Java 17+ (for running tests locally)

## Running

```bash
make run PORT=8080
```

The API will be available at `http://localhost:8080`.

`PORT` defaults to `8080` if not specified:

```bash
make run          # starts on :8080
make run PORT=9090  # starts on :9090
```

## Testing

```bash
make test
```

Tests use an in-memory H2 database — no Docker required.

## Stopping

```bash
make stop    # stops containers
make clean   # stops containers and removes volumes
```

## High Availability

The service runs as two independent application instances (`app1`, `app2`) behind Nginx. If one instance is killed (e.g. via `POST /chaos`), Nginx automatically routes all traffic to the remaining instance. The service stays available.

To simulate:
```bash
curl -X POST http://localhost:8080/chaos
# one instance dies — service still responds
curl http://localhost:8080/stocks
```

## API Reference

### Stocks (Bank)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/stocks` | Get all stocks in the bank |
| `POST` | `/stocks` | Set bank stock quantities |

**POST /stocks** — set bank state:
```json
{"stocks": [{"name": "apple", "quantity": 10}]}
```

**GET /stocks** — response:
```json
{"stocks": [{"name": "apple", "quantity": 9}]}
```

---

### Wallets

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/wallets/{wallet_id}` | Get wallet contents |
| `GET` | `/wallets/{wallet_id}/stocks/{stock_name}` | Get quantity of one stock in wallet |
| `POST` | `/wallets/{wallet_id}/stocks/{stock_name}` | Buy or sell a stock |

**POST /wallets/{wallet_id}/stocks/{stock_name}** — buy or sell:
```json
{"type": "buy"}
```
```json
{"type": "sell"}
```

Response codes:
- `200` — success
- `400` — no stock available in bank (buy) or wallet (sell)
- `404` — stock does not exist

Wallet is created automatically on first operation.

**GET /wallets/{wallet_id}** — response:
```json
{"id": "w1", "stocks": [{"name": "apple", "quantity": 1}]}
```

**GET /wallets/{wallet_id}/stocks/{stock_name}** — response:
```
1
```

---

### Audit Log

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/log` | Get all wallet operations in order |

**GET /log** — response:
```json
{
  "log": [
    {"type": "buy", "wallet_id": "w1", "stock_name": "apple"},
    {"type": "sell", "wallet_id": "w1", "stock_name": "apple"}
  ]
}
```

Only successful wallet operations are logged. Bank operations (`POST /stocks`) are excluded.

---

### Chaos

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/chaos` | Kills the instance handling this request |

---

## API Documentation

Swagger UI is available at:
```
http://localhost:8080/swagger-ui/index.html
```

## Tech Stack

- **Java 17**
- **Spring Boot 3.4.5** (Web, Data JPA, Validation, Actuator)
- **PostgreSQL 15**
- **Flyway** — database migrations
- **Nginx** — load balancer
- **Docker Compose** — orchestration
- **Gradle** — build tool
- **H2** — in-memory database for tests