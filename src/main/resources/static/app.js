async function loadDashboard() {
    try {
        const response = await fetch("/api/dashboard");
        if (!response.ok) throw new Error("Dashboard API failed");
        const data = await response.json();
        document.getElementById("totalCustomers").textContent = data.totalCustomers;
        document.getElementById("totalAccounts").textContent = data.totalAccounts;
        document.getElementById("totalBalance").textContent = "₹" + data.totalBalance;
        document.getElementById("totalTransactions").textContent = data.totalTransactions;
    } catch (error) {
        console.error("Dashboard Error:", error);
    }
}

document.addEventListener("DOMContentLoaded", loadDashboard);
