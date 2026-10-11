async function loadTransactions() {
    const accountNo = document.getElementById("accountNo").value.trim();
    const message = document.getElementById("transactionMessage");
    const tbody = document.getElementById("transactionBody");
    if (!accountNo) {
        message.textContent = "⚠️ Please enter account number";
        return;
    }
    try {
        const response = await fetch(`/api/transactions/${encodeURIComponent(accountNo)}`);
        const text = await response.text();
        if (!response.ok) throw new Error(text);
        const data = JSON.parse(text);
        tbody.innerHTML = "";
        if (data.length === 0) {
            message.textContent = "ℹ️ No transactions found";
            return;
        }
        data.forEach(t => {
            const row = document.createElement("tr");
            row.innerHTML = `<td>${t.transactionType}</td><td>₹${t.amount}</td><td>₹${t.balanceAfterTransaction}</td><td>${t.transactionDate}</td><td>${t.description}</td>`;
            tbody.appendChild(row);
        });
        message.textContent = `✅ ${data.length} transaction(s) found`;
    } catch (error) {
        console.error("Transaction History Error:", error);
        tbody.innerHTML = "";
        message.textContent = "❌ " + error.message;
    }
}
