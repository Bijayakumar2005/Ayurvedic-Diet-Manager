document.addEventListener("DOMContentLoaded", () => {
    const chatForm = document.getElementById("chat-form");
    const chatInput = document.getElementById("chat-input");
    const chatMessages = document.getElementById("chat-messages");

    // Function to add messages to chat window
    function addMessage(sender, message) {
    const chatMessages = document.getElementById("chat-messages");
    const msgDiv = document.createElement("div");
    msgDiv.classList.add("message", sender);

    // Convert markdown-like bullets into HTML list
    let formattedMessage = message
        .replace(/\n-/g, "</li><li>")
        .replace(/^-/g, "<li>") + "</li>";

    if (formattedMessage.includes("<li>")) {
        formattedMessage = "<ul>" + formattedMessage + "</ul>";
    }

    msgDiv.innerHTML = `<strong>${sender}:</strong> ${formattedMessage}`;
    chatMessages.appendChild(msgDiv);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}


    // Handle form submit
    chatForm.addEventListener("submit", function (e) {
        e.preventDefault();
        const userMessage = chatInput.value.trim();

        if (!userMessage) {
            appendMessage("Bot", "⚠️ Message cannot be empty!", true);
            return;
        }

        // Show user message
        appendMessage("You", userMessage);
        chatInput.value = "";

        // Send to backend
        fetch("/api/chat/chat", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ message: userMessage })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.text();
            })
            .then(data => {
                appendMessage("Bot", data);
            })
            .catch(error => {
                console.error("Error:", error);
                appendMessage("Bot", "⚠️ Error connecting to server!", true);
            });
    });
});
