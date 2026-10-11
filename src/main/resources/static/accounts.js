
let allAccounts = [];

// LOAD ACCOUNTS
async function loadAccounts() {
    const body = document.getElementById("accountTableBody");
    const message = document.getElementById("accountMessage");

    try {
        const response = await fetch("/api/accounts");

        if (!response.ok) {
            throw new Error("Failed to load accounts");
        }

        allAccounts = await response.json();

        const searchInput = document.getElementById("accountSearch");
        searchAccounts(searchInput ? searchInput.value : "");

    } catch (error) {
        console.error("Account Error:", error);
        message.textContent = "❌ Unable to load accounts.";
    }
}

// SEARCH ACCOUNTS BY CUSTOMER NAME, ACCOUNT NUMBER OR CUSTOMER ID
function searchAccounts(searchText = "") {
    const body = document.getElementById("accountTableBody");
    const message = document.getElementById("accountMessage");

    const search = searchText.trim().toLowerCase();

    const filteredAccounts = allAccounts.filter(a =>
        String(a.customer?.fullName ?? "")
            .toLowerCase().includes(search) ||

        String(a.accountNo ?? "")
            .toLowerCase().includes(search) ||

        String(a.customer?.customerId ?? "")
            .toLowerCase().includes(search)
    );

    body.innerHTML = "";

    if (filteredAccounts.length === 0) {
        message.textContent = search
            ? "ℹ️ No matching accounts found."
            : "ℹ️ No accounts found.";
        return;
    }

    filteredAccounts.forEach(a => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${a.accountId}</td>
            <td>${a.accountNo ?? ""}</td>
            <td>${a.customer?.customerId ?? "N/A"}</td>
            <td></td>
            <td>${a.accountType ?? ""}</td>
            <td>₹${a.balance ?? 0}</td>
            <td>${a.branch ?? ""}</td>
            <td>${a.ifscCode ?? ""}</td>
            <td>${a.status ?? ""}</td>
            <td></td>
        `;

        // Set customer name safely
        row.cells[3].textContent =
            a.customer?.fullName ?? "N/A";

        // EDIT BUTTON
        const editButton = document.createElement("button");
        editButton.textContent = "✏️ Edit";
        editButton.addEventListener("click", () => {
            editAccount(a.accountId);
        });

        // DELETE BUTTON
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "🗑️ Delete";
        deleteButton.addEventListener("click", () => {
            deleteAccount(a.accountId);
        });

        row.cells[9].appendChild(editButton);
        row.cells[9].appendChild(deleteButton);

        body.appendChild(row);
    });

    message.textContent =
        `✅ ${filteredAccounts.length} account(s) found.`;
}

// EDIT ACCOUNT
async function editAccount(id) {
    const accountType = prompt(
        "Enter Account Type (SAVINGS/CURRENT):"
    );

    if (accountType === null || accountType.trim() === "") {
        return;
    }

    const branch = prompt("Enter Branch Name:");

    if (branch === null || branch.trim() === "") {
        return;
    }

    const ifscCode = prompt("Enter IFSC Code:");

    if (ifscCode === null || ifscCode.trim() === "") {
        return;
    }

    const status = prompt(
        "Enter Status (ACTIVE/INACTIVE):"
    );

    if (status === null || status.trim() === "") {
        return;
    }

    const updatedAccount = {
        accountType: accountType.trim().toUpperCase(),
        branch: branch.trim(),
        ifscCode: ifscCode.trim().toUpperCase(),
        status: status.trim().toUpperCase()
    };

    try {
        const response = await fetch(`/api/accounts/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedAccount)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Failed to update account");
        }

        alert("✅ Account updated successfully!");
        await loadAccounts();

    } catch (error) {
        console.error("Update Error:", error);

        alert(
            "❌ Unable to update account.\n" +
            error.message
        );
    }
}

// DELETE ACCOUNT
async function deleteAccount(id) {
    const confirmed = confirm(
        "Are you sure you want to delete this account?"
    );

    if (!confirmed) {
        return;
    }

    try {
        const response = await fetch(`/api/accounts/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Failed to delete account");
        }

        alert("✅ Account deleted successfully!");
        await loadAccounts();

    } catch (error) {
        console.error("Delete Error:", error);

        alert(
            "❌ Unable to delete account.\n" +
            error.message
        );
    }
}

// INITIALIZE PAGE
document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("accountSearch");

    if (searchInput) {
        searchInput.addEventListener("input", function () {
            searchAccounts(this.value);
        });
    }

    loadAccounts();
});
