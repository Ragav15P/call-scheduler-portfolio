📞 Call Scheduler Portfolio
A personal portfolio website enhanced with a powerful call scheduling system powered by the Google Calendar API. Visitors can seamlessly book 1:1 video calls (Google Meet) with the portfolio owner — combining professionalism with automation.



✨ Key Highlights
✅ Book 1:1 Google Meet video calls directly from the website

📩 Automatic email confirmations upon successful scheduling

🗓️ Real-time sync with Google Calendar

🧑‍💼 Personal portfolio sections: About Me, Projects, Contact

🌐 Fully responsive UI — built with HTML, CSS, JavaScript

🔐 Secrets secured using environment variables for production safety

⚡ Instant deployment via Render with Docker support




🛠 Tech Stack
Layer	Tech Used
Backend	Java, Spring Boot, Spring Security, JWT
Frontend	HTML, CSS, JavaScript
Database	H2 (in-memory, can be switched to MySQL)
APIs	Google Calendar API, JavaMailSender
DevOps	Docker, Render.com, Environment Variables

📁 Folder Structure


CallScheduler-Portfolio-main/
├── src/
│   └── main/
│       ├── java/in/ragav/personalportfolio/   # Backend code
│       ├── resources/                         # App configs & templates
│       ├── static/                            # Static frontend assets
│       └── templates/                         # HTML templates (if using Thymeleaf)
├── .env                # Environment variables (not committed to Git)
├── Dockerfile          # Optional Docker support
├── render.yaml         # Configuration for Render deployment
├── pom.xml             # Maven dependencies & build config
└── README.md           # You're here!



🚀 Run Locally in 3 Easy Steps




# Step 1: Clone the repo
git clone https://github.com/Ragav15P/call-scheduler-portfolio.git
cd call-scheduler-portfolio

# Step 2: Configure environment variables
# Create a .env file in the root and add the required credentials

# Step 3: Run the Spring Boot application
./mvnw spring-boot:run



🌐 Visit http://localhost:9090 once the server is up.


📸 Screenshots





<img width="932" alt="image" src="https://github.com/user-attachments/assets/b41d4bea-6db9-447a-8036-8e95fe5dd1ab" />



<img width="928" alt="image" src="https://github.com/user-attachments/assets/cfff6cee-e371-492b-aa68-9fddb4a418e5" />


<img width="926" alt="image" src="https://github.com/user-attachments/assets/93ddaa00-b78f-4c63-a056-ee8042df1154" />


<img width="893" alt="image" src="https://github.com/user-attachments/assets/a1c0653b-c56d-476c-b950-3ea319690c7d" />


<img width="932" alt="image" src="https://github.com/user-attachments/assets/103705ca-9c36-4537-be08-97665babbeb7" />



<img width="918" alt="image" src="https://github.com/user-attachments/assets/b5279133-f2fb-44a8-b01c-65e0a203451c" />



<img width="856" alt="image" src="https://github.com/user-attachments/assets/88ef489e-958c-4712-b020-e546cf2e10d8" />



📜 License
Licensed under the MIT License — feel free to use, modify, and share!








