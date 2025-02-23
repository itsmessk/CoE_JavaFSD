import { courses, instructors } from "./data.js";
document.addEventListener("DOMContentLoaded", () => {
    const currentPage = window.location.pathname.split("/").pop();
    const urlParams = new URLSearchParams(window.location.search);
    const courseId = urlParams.get("id");
    const instructorId = urlParams.get("id");
    switch (currentPage) {
        case "":
        case "index.html":
            initHomePage();
            break;
        case "courses.html":
            initCoursesPage();
            break;
        case "instructors.html":
            initInstructorsPage();
            break;
        case "enrollment.html":
            initEnrollmentPage();
            break;
        case "course.html":
            if (courseId) {
                renderCourseDetails(parseInt(courseId));
            }
            break;
        case "instructor.html":
            if (instructorId) {
                renderInstructorDetails(parseInt(instructorId));
            }
            break;
    }
});
function renderInstructorDetails(instructorId) {
    const instructor = instructors.find((i) => i.id === instructorId);
    if (!instructor) {
        document.body.innerHTML = "<h2>Instructor Not Found</h2>";
        return;
    }
    const instructorContainer = document.getElementById("instructor-details");
    const instructorCourses = courses.filter((c) => c.instructor === instructor.name);
    instructorContainer.innerHTML = `
        <h1>${instructor.name}</h1>
        <img src="${instructor.image}" alt="${instructor.name}">
        <p><strong>Expertise:</strong> ${instructor.expertise}</p>
        <h3>Courses Taught:</h3>
        <ul>
            ${instructorCourses.map((c) => `<li><a href="course.html?id=${c.id}">${c.title}</a></li>`).join("")}
        </ul>
        <h3>User Ratings:</h3>
        <p>⭐ 4.5/5 (Based on 120 reviews)</p>
    `;
}
function renderCourseDetails(courseId) {
    const course = courses.find((c) => c.id === courseId);
    if (!course) {
        document.body.innerHTML = "<h2>Course Not Found</h2>";
        return;
    }
    const courseContainer = document.getElementById("course-details");
    courseContainer.innerHTML = `
        <h1>${course.title}</h1>
        <img src="${course.image}" alt="${course.title}">
        <p><strong>Instructor:</strong> ${course.instructor}</p>
        <p><strong>Category:</strong> ${course.category}</p>
        <p><strong>Duration:</strong> ${course.duration}</p>
        <p><strong>Price:</strong> $${course.price}</p>
        <p><strong>Prerequisites:</strong> Basic knowledge required.</p>
        <h3>User Reviews:</h3>
        <ul id="reviews">
            <li>Great course! - John</li>
            <li>Very informative. - Sarah</li>
        </ul>
    `;
}
function initHomePage() {
    renderFeaturedCourses();
    initTabs();
}
function initCoursesPage() {
    renderAllCourses();
    initSearch();
}
function initInstructorsPage() {
    renderInstructors();
}
function initEnrollmentPage() {
    populateCourseSelect();
    initEnrollmentForm();
}
function renderFeaturedCourses() {
    const carousel = document.querySelector(".carousel");
    const featuredCourses = courses.filter((course) => course.featured);
    featuredCourses.forEach((course) => {
        const courseCard = createCourseCard(course);
        carousel.appendChild(courseCard);
    });
}
function initTabs() {
    const tabButtons = document.querySelectorAll(".tab-button");
    const tabContent = document.querySelector(".tab-content");
    tabButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const category = button.dataset.category;
            tabButtons.forEach((btn) => btn.classList.remove("active"));
            button.classList.add("active");
            if (category) {
                renderCoursesByCategory(category);
            }
        });
    });
    // Initialize with 'All' category
    renderCoursesByCategory("all");
}
function renderCoursesByCategory(category) {
    const tabContent = document.querySelector(".tab-content");
    tabContent.innerHTML = "";
    const filteredCourses = category === "all" ? courses : courses.filter((course) => course.category === category);
    filteredCourses.forEach((course) => {
        const courseCard = createCourseCard(course);
        tabContent.appendChild(courseCard);
    });
}
function renderAllCourses() {
    const coursesGrid = document.querySelector(".courses-grid");
    courses.forEach((course) => {
        const courseCard = createCourseCard(course);
        coursesGrid.appendChild(courseCard);
    });
}
function initSearch() {
    const searchInput = document.getElementById("course-search");
    searchInput.addEventListener("input", () => {
        const searchTerm = searchInput.value.toLowerCase();
        const filteredCourses = courses.filter((course) => course.title.toLowerCase().includes(searchTerm) || course.instructor.toLowerCase().includes(searchTerm));
        renderFilteredCourses(filteredCourses);
    });
}
function renderFilteredCourses(filteredCourses) {
    const coursesGrid = document.querySelector(".courses-grid");
    coursesGrid.innerHTML = "";
    filteredCourses.forEach((course) => {
        const courseCard = createCourseCard(course);
        coursesGrid.appendChild(courseCard);
    });
}
function renderInstructors() {
    const instructorsGrid = document.querySelector(".instructors-grid");
    instructors.forEach((instructor) => {
        const instructorCard = createInstructorCard(instructor);
        instructorsGrid.appendChild(instructorCard);
    });
}
function populateCourseSelect() {
    const courseSelect = document.getElementById("course-select");
    courses.forEach((course) => {
        const option = document.createElement("option");
        option.value = course.id.toString();
        option.textContent = course.title;
        courseSelect.appendChild(option);
    });
}
function initEnrollmentForm() {
    const form = document.getElementById("enrollment-form");
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const enrollmentData = Object.fromEntries(formData.entries());
        console.log("Enrollment data:", enrollmentData);
        alert("Enrollment submitted successfully!");
        form.reset();
    });
}
function createCourseCard(course) {
    const card = document.createElement("div");
    card.className = "course-card";
    card.innerHTML = `
        <a href="course.html?id=${course.id}">
            <img src="${course.image}" alt="${course.title}">
            <div class="course-card-content">
                <h3>${course.title}</h3>
                <p>Instructor: ${course.instructor}</p>
                <p>Duration: ${course.duration}</p>
                <p>Price: $${course.price}</p>
            </div>
        </a>
    `;
    return card;
}
function createInstructorCard(instructor) {
    const card = document.createElement("div");
    card.className = "instructor-card";
    card.innerHTML = `
        <a href="instructor.html?id=${instructor.id}">
            <img src="${instructor.image}" alt="${instructor.name}">
            <div class="instructor-card-content">
                <h3>${instructor.name}</h3>
                <p>${instructor.expertise}</p>
            </div>
        </a>
    `;
    return card;
}
