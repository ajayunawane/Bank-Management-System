document.getElementById("depositForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const accountNo = document.getElementById("accountNo").value;
    const amount = Number(document.getElementById("amount").value);
    try {
        const response = await fetch(`/api/transactions/deposit?accountNo=${encodeURIComponent(accountNo)}&amount=${amount}`, {method: "POST"});
        const text = await response.text();
        console.log("Backend Response:", text);
        if (!response.ok) throw new Error(text);
        const data = JSON.parse(text);
        document.getElementById("depositMessage").textContent = "✅ Deposit Successful! New Balance: ₹" + data.balanceAfterTransaction;
        document.getElementById("depositForm").reset();
    } catch (error) {
        console.error("Deposit Error:", error);
        document.getElementById("depositMessage").textContent = "❌ Deposit Failed";
    }
});
