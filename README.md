# 🌿 Ayurvedic Diet Manager

**A Smart Healthcare Web Application Integrating Ayurveda with Modern Nutrition**

Ayurvedic Diet Manager is a **full-stack Spring Boot web application** that enables doctors and patients to manage **personalized Ayurvedic diet plans**, appointments, and food recommendations based on **Dosha principles (Vata, Pitta, Kapha)** combined with modern nutritional data.

This project follows **clean MVC architecture**, role-based access, and is designed as a **major academic & SIH-ready healthcare solution**.

---

## 📌 Project Overview

Ayurveda focuses on personalized health and diet. This platform digitizes that concept by providing:

- Ayurvedic food & nutrition database
- Doctor–patient interaction system
- Personalized diet plan management
- Appointment scheduling
- Secure authentication & role-based access
- Modern, responsive UI with dark mode
- Chatbot & PDF support (extensible)

---

## ✨ Key Features

### 👨‍⚕️ Doctor (Practitioner)
- Secure login & dashboard
- View assigned patients
- Create & manage diet plans
- View patient appointments
- Access patient health data

### 👤 Patient
- Secure signup & login
- View diet plans
- Explore Ayurvedic food database
- Book appointments with doctors

### 🍎 Food Nutritional Database
- Search by name, category, or properties
- Nutritional values:
  - Calories, Protein, Carbohydrates, Fat, Fiber
- Ayurvedic properties:
  - **Rasa (Taste)**
  - **Virya (Energy)**
  - **Vipaka (Post-digestive effect)**
  - **Dosha impact**

### 📅 Appointments
- Appointment booking
- Status tracking (Pending / Approved)
- Stored securely in database

### 🤖 Extra Capabilities
- Chatbot integration (Gemini Service)
- PDF generation support
- File upload utilities
- Dark Mode UI

---

## 🛠️ Tech Stack

### Backend
- **Java**
- **Spring Boot**
- Spring MVC
- Spring Data JPA
- REST APIs

### Frontend
- Thymeleaf
- HTML5
- CSS3
- JavaScript

### Database
- **H2 Database**
- Schema-based relational design

### Tools
- Git & GitHub
- VS Code / IntelliJ IDEA
- H2 Console
- Maven

---

## 🏗️ Architecture (MVC)
Controller → Service → Repository → Database
↓
Thymeleaf Views (UI)


---

## 📂 Project Structure (Actual)

AYURVEDIC-DIET-MANAGER/
│
├── .vscode/
│ └── settings.json
│
├── data/
│ ├── ayurdietdb.mv.db
│ └── ayurdietdb.trace.db
│
├── src/main/java/com/ayurdiet/
│ ├── config/
│ │ ├── AppConfig.java
│ │ └── WebConfig.java
│ │
│ ├── controller/
│ │ ├── AuthController.java
│ │ ├── DashboardController.java
│ │ ├── FoodController.java
│ │ ├── DietPlanController.java
│ │ ├── PractitionerController.java
│ │ ├── UserController.java
│ │ ├── ChatRestController.java
│ │ └── CustomErrorController.java
│ │
│ ├── model/
│ │ ├── User.java
│ │ ├── Practitioner.java
│ │ ├── Food.java
│ │ ├── DietPlan.java
│ │ ├── Meal.java
│ │ ├── FoodItem.java
│ │ └── Appointment.java
│ │
│ ├── repository/
│ │ ├── UserRepository.java
│ │ ├── PractitionerRepository.java
│ │ ├── FoodRepository.java
│ │ ├── DietPlanRepository.java
│ │ └── AppointmentRepository.java
│ │
│ ├── service/
│ │ ├── UserService.java
│ │ ├── PractitionerService.java
│ │ ├── FoodService.java
│ │ │
│ │ ├── impl/
│ │ │ ├── AppointmentServiceImpl.java
│ │ │ └── PractitionerServiceImpl.java
│ │
│ ├── util/
│ │ └── FileUploadUtil.java
│ │
│ └── AyurDietApplication.java
│
├── src/main/resources/
│ ├── static/
│ │ ├── css/style.css
│ │ ├── js/script.js
│ │ ├── js/chatbot.js
│ │ └── images/logo.png
│ │
│ ├── templates/
│ │ ├── fragments/header.html
│ │ ├── fragments/footer.html
│ │ ├── index.html
│ │ ├── login.html
│ │ ├── signup.html
│ │ ├── dashboard.html
│ │ ├── food-search.html
│ │ ├── food-detail.html
│ │ ├── diet-plan-create.html
│ │ ├── diet-plan-list.html
│ │ ├── diet-plan-view.html
│ │ ├── practitioner-view.html
│ │ ├── practitioner-appointments.html
│ │ └── error.html
│ │
│ ├── application.properties
│ └── schema.sql
│
└── README.md


---

## 🗄️ Database (H2)

### Main Tables
- USERS
- PRACTITIONERS
- FOODS
- DIET_PLANS
- APPOINTMENTS

### Example: APPOINTMENTS
| Column | Description |
|------|------------|
| ID | Appointment ID |
| PATIENT_NAME | Patient Name |
| PATIENT_EMAIL | Email |
| PATIENT_PHONE | Phone |
| APPOINTMENT_DATE_TIME | Date & Time |
| STATUS | Pending / Approved |
| PRACTITIONER_ID | Doctor ID |

---

## ▶️ How to Run the Project

### 1️⃣ Clone Repository

git clone https://github.com/your-username/Ayurvedic-Diet-Manager.git
### 2️⃣ Open in IDE

IntelliJ IDEA or VS Code

Ensure Java & Maven are installed

### 3️⃣ Run Spring Boot App
Run AyurDietApplication.java
### 4️⃣ Access Application
http://localhost:8080
### 5️⃣ H2 Console
http://localhost:8080/h2-console
## 📸 Screenshots

Includes:

Home Page

Login / Signup

Doctor Dashboard

Food Search & Details

Appointment Records

H2 Database View

📁 Stored in screenshots/
## 🎯 Use Cases

Digital healthcare systems

Ayurvedic diet recommendation platforms

Doctor–patient management

SIH (Smart India Hackathon) project

Major academic project

Resume & interview showcase

## 🚀 Future Enhancements

AI-based diet recommendations

Chatbot intelligence improvements

Cloud database integration

Mobile application

Role-based security (Spring Security)

Analytics dashboard

## 👤 Author

Bijaya Kumar Rout
GitHub: https://github.com/Bijayakumar2005

## ⭐ Support

If you like this project, give it a ⭐ on GitHub!
