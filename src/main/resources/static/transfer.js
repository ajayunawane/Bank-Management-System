document.getElementById("transferForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const fromAccountNo = document.getElementById("fromAccountNo").value;
    const toAccountNo = document.getElementById("toAccountNo").value;
    const amount = Number(document.getElementById("amount").value);
    try {
        const response = await fetch(`/api/transactions/transfer?fromAccountNo=${encodeURIComponent(fromAccountNo)}&toAccountNo=${encodeURIComponent(toAccountNo)}&amount=${amount}`, {method: "POST"});
        const text = await response.text();
        console.log("Backend Response:", text);
        if (!response.ok) throw new Error(text);
        const data = JSON.parse(text);
        document.getElementById("transferMessage").textContent = "✅ Transfer Successful! Remaining Balance: ₹" + data.balanceAfterTransaction;
        document.getElementById("transferForm").reset();
    } catch (error) {
        console.error("Transfer Error:", error);
        document.getElementById("transferMessage").textContent = "❌ Transfer Failed: " + error.message;
    }
});
