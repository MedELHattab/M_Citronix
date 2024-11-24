# Citronix Management System

The Citronix Management System is a comprehensive application for managing farms, fields, harvests, and related sales. It is built using Java and utilizes Jakarta Persistence API (JPA) for database interactions.

---

## Features

- **Farm Management**
  - Create and manage farms with associated fields and harvests.

- **Field Management**
  - Add fields to a farm with validation for area constraints.
  - Manage fields associated with farms.

- **Harvest Management**
  - Create harvest records for fields with details of harvested trees and their productivity.
  - Maintain seasonal data for better insights.

- **Tree Management**
  - Track individual trees, their planting dates, and calculate annual productivity.
  - Validate planting seasons.

- **Sales Management**
  - Record and manage sales data linked to harvests.
  - Automatically calculate revenue based on quantity and unit price.

---

## Class Structure

The system includes the following main entities:

- **Farm**:
  - Represents a farm with fields.
  - Fields are composed within the farm.

- **Field**:
  - Represents a specific area in a farm.
  - Linked to a farm and contains harvests.

- **Tree**:
  - Represents trees planted in fields.
  - Calculates annual productivity and validates planting seasons.

- **Harvest**:
  - Represents the seasonal harvest of a field.
  - Linked to specific trees via harvest details.

- **HarvestDetail**:
  - Tracks the productivity of individual trees during a harvest.

- **Sale**:
  - Tracks the sale of harvested products.
  - Calculates revenue automatically.

---

## Relationships

- **Farm-Field**: Composition (a farm is composed of multiple fields).
- **Field-Tree**: Aggregation (fields contain trees).
- **Field-Harvest**: Aggregation (fields have multiple harvests).
- **Harvest-HarvestDetail**: Aggregation (harvest details belong to a harvest).
- **Harvest-Sale**: Aggregation (sales are linked to harvests).

---

## Technology Stack

- **Programming Language**: Java
- **Framework**: Spring Boot
- **Database**: PostgreSQL 
- **Build Tool**: Maven

---

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/MedELHattab/M_Citronix.git
   cd M_Citronix

---
##   application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/citronix
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
