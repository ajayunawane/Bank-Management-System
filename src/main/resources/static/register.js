document.getElementById("customerForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const customer = {
        fullName: fullName.value,
        dateOfBirth: dateOfBirth.value,
        gender: gender.value,
        email: email.value,
        contactNo: contactNo.value,
        address: address.value,
        city: city.value
    };
    try {
        const response = await fetch("/api/customers", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(customer)
        });
        const text = await response.text();
        if (!response.ok) throw new Error(text);
        const data = JSON.parse(text);
        document.getElementById("customerMessage").textContent = "✅ Customer Registered! Customer ID: " + data.customerId;
        document.getElementById("customerForm").reset();
    } catch (error) {
        console.error(error);
        document.getElementById("customerMessage").textContent = "❌ Registration Failed";
    }
});
