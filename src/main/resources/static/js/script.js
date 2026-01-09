// Login and Sign Up button actions
document.addEventListener('DOMContentLoaded', function() {
    const loginBtn = document.getElementById('login-btn');
    const signupBtn = document.getElementById('signup-btn');
    
    if (loginBtn) {
        loginBtn.onclick = function() {
            window.location.href = "/login";
        };
    }
    
    if (signupBtn) {
        signupBtn.onclick = function() {
            window.location.href = "/signup";
        };
    }

    // Dark/Light mode toggle
    const themeToggle = document.getElementById('theme-toggle');
    let darkMode = localStorage.getItem('darkMode') === 'true';

    // Apply saved theme
    if (darkMode) {
        document.body.classList.add('dark-mode');
        if (themeToggle) themeToggle.textContent = "Light Mode";
    }

    if (themeToggle) {
        themeToggle.addEventListener('click', function() {
            darkMode = !darkMode;
            localStorage.setItem('darkMode', darkMode);
            
            if (darkMode) {
                document.body.classList.add('dark-mode');
                themeToggle.textContent = "Light Mode";
            } else {
                document.body.classList.remove('dark-mode');
                themeToggle.textContent = "Dark Mode";
            }
        });
    }

    // Handle image preview in profile page
    const photoInput = document.getElementById('photoInput');
    const profileImg = document.getElementById('profileImg');
    const removePhoto = document.getElementById('removePhoto');

    if (photoInput && profileImg) {
        photoInput.addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    profileImg.src = e.target.result;
                };
                reader.readAsDataURL(file);
            }
        });
    }

    if (removePhoto && profileImg) {
        removePhoto.addEventListener('click', function() {
            if (confirm('Remove profile photo?')) {
                profileImg.src = 'https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=60';
                // In a real application, you would send a request to remove the photo
            }
        });
    }

    // Close dropdown when clicking outside
    document.addEventListener('click', function(event) {
        const dropdowns = document.querySelectorAll('.profile-dropdown');
        dropdowns.forEach(function(dropdown) {
            if (!dropdown.contains(event.target)) {
                const dropdownContent = dropdown.querySelector('.dropdown-content');
                if (dropdownContent) {
                    dropdownContent.style.display = 'none';
                }
            }
        });
    });
});