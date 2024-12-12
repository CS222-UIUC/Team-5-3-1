# Team-5-3-1

# Introduction

What is the 5-3-1 restaurant picker? 

Our website 5-3-1 helps users find and choose restaurants. Given a location, search radius, and restaurant-specific parameters, 5-3-1 randomly generates 5 restaurants. Users are prompted to select 3 restaurants. 5-3-1 then randomly picks 1 restaurant

For more details, view the full project proposal [here](https://docs.google.com/document/d/1jMbYF-eEGSsqXW_8F-txWdptcNTEeNfXWTZL4NA-CbA/edit?usp=sharing).

# Technical Architecture 
![Technical Architecture Drawing](https://github.com/CS222-UIUC/Team-5-3-1/blob/new-branch/Screen%20Shot%202024-12-11%20at%209.23.45%20PM.png)

# Developers

Nyssa Aftab - 

Nat Gao -

Nancy Wang - 

Elaina Xiao - Worked on frontend pages, restaurant cards, and filtering with backend

# Environment Setup
## Backend

Set up API as environment variable

Navigate to your source directory run the following command

cd backend

cd fivethreeone

./mvnw clean package -DskipTests

in backend directory run:

docker build -t backend-springboot .

docker run -d -p 8081:8080 backend-springboot

when done run: 

docker stop 3e56942ba023

## Frontend

To start frontend navigate to your source directory and run

cd frontend

npm install if not installed already

npm start
