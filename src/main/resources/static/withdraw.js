document.getElementById("withdrawForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const accountNo = document.getElementById("accountNo").value;
    const amount = Number(document.getElementById("amount").value);
    try {
        const response = await fetch(`/api/transactions/withdraw?accountNo=${encodeURIComponent(accountNo)}&amount=${amount}`, {method: "POST"});
        const text = await response.text();
        console.log("Backend Response:", text);
        if (!response.ok) throw new Error(text);
        const data = JSON.parse(text);
        document.getElementById("withdrawMessage").textContent = "✅ Withdraw Successful! Remaining Balance: ₹" + data.balanceAfterTransaction;
        document.getElementById("withdrawForm").reset();
    } catch (error) {
        console.error("Withdraw Error:", error);
        document.getElementById("withdrawMessage").textContent = "❌ Withdraw Failed";
    }
});
