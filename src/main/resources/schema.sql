CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    age INT,
    gender VARCHAR(50),
    weight DOUBLE,
    height DOUBLE,
    notes VARCHAR(1000),
    photo_url VARCHAR(500),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);


CREATE TABLE IF NOT EXISTS practitioners (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255),
  phone VARCHAR(100),
  qualifications VARCHAR(255),
  experience VARCHAR(255),
  specialization VARCHAR(255),
  consultation_fee DECIMAL(10,2),
  bio CLOB,
  image_path VARCHAR(512),
  available BOOLEAN
);

CREATE TABLE IF NOT EXISTS appointments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  practitioner_id BIGINT,
  user_id BIGINT,
  patient_name VARCHAR(255),
  patient_email VARCHAR(255),
  patient_phone VARCHAR(50),
  appointment_date_time TIMESTAMP,
  status VARCHAR(20),
  notes CLOB,
  CONSTRAINT fk_practitioner FOREIGN KEY (practitioner_id) REFERENCES practitioners(id)
  -- optionally foreign key to users table
);
