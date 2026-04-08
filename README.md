# 🌱 Automated Greenhouse Management System (AGMS)

Microservice-Based Application for Smart Agriculture 🌾

---

## 📌 Overview

The **Automated Greenhouse Management System (AGMS)** is a cloud-native microservices platform designed to monitor environmental conditions and automatically control greenhouse operations.

This system integrates with an external IoT API to fetch real-time temperature and humidity data and applies rule-based automation to maintain optimal crop conditions.

---

## 🎯 Business Objectives

* Create and manage greenhouse zones
* Monitor real-time sensor data
* Automatically trigger actions (Fan / Heater)
* Manage crop lifecycle stages

---

## 🏗️ System Architecture

### 🔹 Infrastructure Services

| Service       | Port | Description               |
| ------------- | ---- | ------------------------- |
| Eureka Server | 8761 | Service Discovery         |
| Config Server | 8888 | Centralized Configuration |
| API Gateway   | 8080 | Routing + Security        |

---

### 🔹 Domain Microservices

| Service            | Port | Description               |
| ------------------ | ---- | ------------------------- |
| Zone Service       | 8081 | Manage zones & thresholds |
| Sensor Service     | 8082 | Fetch IoT telemetry       |
| Automation Service | 8083 | Rule engine               |
| Crop Service       | 8084 | Crop lifecycle management |

---

## ⚙️ Technologies Used

* Spring Boot
* Spring Cloud (Eureka, Config, Gateway)
* OpenFeign / RestTemplate
* MongoDB / MySQL
* REST APIs
* JWT Authentication

---

## 🔌 External IoT API

Base URL:
http://104.211.95.241:8080/api

---

### Authentication APIs

POST /auth/register
POST /auth/login
POST /auth/refresh

---

### Device & Telemetry

POST /devices
GET /devices
GET /devices/telemetry/{deviceId}

(Requires Authorization: Bearer Token)

---

## 🔄 System Workflow

1. User creates a Zone
2. Zone Service registers device via IoT API
3. Sensor Service fetches telemetry every 10 seconds
4. Sensor Service sends data to Automation Service
5. Automation Service:

   * Gets zone limits
   * Applies rules:

     * Temp > max → TURN_FAN_ON
     * Temp < min → TURN_HEATER_ON
6. Logs are saved
7. User can view logs

---

## 🚀 How to Run the System

### Step 1: Start Infrastructure

1. Start Eureka Server
2. Start Config Server (Optional)
3. Start API Gateway

---

### Step 2: Start Microservices

Run in order:

1. Zone Service (8081)
2. Sensor Service (8082)
3. Automation Service (8083)
4. Crop Service (8084)

---

### Step 3: Verify

Open:
http://localhost:8761

✔ All services should be **UP**

---

## 📡 API Endpoints

### Zone Service

POST   /api/zones
GET    /api/zones/{id}
PUT    /api/zones/{id}
DELETE /api/zones/{id}

---

### Sensor Service

GET /api/sensors/latest

---

### Automation Service

POST /api/automation/process
GET  /api/automation/logs

---

### Crop Service

POST /api/crops
PUT  /api/crops/{id}/status
GET  /api/crops

---

## 🌿 Crop Lifecycle

* SEEDLING
* VEGETATIVE
* HARVESTED

---

## 🔐 Security

All requests must include:

Authorization: Bearer <token>

JWT validation is handled at API Gateway.

---

## 🗄️ Databases

| Service            | Database  |
| ------------------ | --------- |
| Zone Service       | MySQL     |
| Sensor Service     | Temporary |
| Automation Service | MongoDB   |
| Crop Service       | MongoDB   |

---

## 📁 Project Structure

AGMS/
├── eureka-server/
├── config-server/
├── api-gateway/
├── zone-service/
├── sensor-service/
├── automation-service/
├── crop-service/
└── postman_collection.json

---

## 📬 Postman Collection

Include exported Postman collection in project root.

---

## 📸 Screenshots

Include Eureka dashboard screenshot in /docs folder.

---

## ✅ Features

✔ Microservices Architecture
✔ Service Discovery (Eureka)
✔ API Gateway Routing
✔ External IoT Integration
✔ Scheduled Data Fetching
✔ Rule Engine Automation
✔ MongoDB Integration
✔ Crop Lifecycle Management

---

## 👩‍💻 Author

Dilmi Kaushalya
Graduate Diploma in Software Engineering – IJSE

---
