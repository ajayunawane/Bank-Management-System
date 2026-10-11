
let allCustomers = [];

// LOAD CUSTOMERS
async function loadCustomers() {
    const body = document.getElementById("customerTableBody");
    const message = document.getElementById("customerMessage");

    try {
        const response = await fetch("/api/customers");

        if (!response.ok) {
            throw new Error("Failed to load customers");
        }

        allCustomers = await response.json();

        displayCustomers(
            document.getElementById("customerSearch").value
        );

    } catch (error) {
        console.error("Customer Error:", error);
        message.textContent = "❌ Unable to load customers.";
    }
}

// SEARCH CUSTOMERS
function loadCustomerSearch() {
    const searchInput = document.getElementById("customerSearch");

    searchInput.addEventListener("input", function () {
        displayCustomers(this.value);
    });
}

// DISPLAY / FILTER CUSTOMERS
function displayCustomers(searchText = "") {
    const body = document.getElementById("customerTableBody");
    const message = document.getElementById("customerMessage");

    const search = searchText.trim().toLowerCase();

    const filteredCustomers = allCustomers.filter(c =>
        String(c.customerId ?? "").toLowerCase().includes(search) ||
        (c.fullName ?? "").toLowerCase().includes(search) ||
        (c.email ?? "").toLowerCase().includes(search) ||
        String(c.contactNo ?? "").toLowerCase().includes(search)
    );

    body.innerHTML = "";

    if (filteredCustomers.length === 0) {
        message.textContent = search
            ? "ℹ️ No matching customers found."
            : "ℹ️ No customers found.";
        return;
    }

    filteredCustomers.forEach(c => {
        const row = document.createElement("tr");

        // Create table cells safely
        const values = [
            c.customerId,
            c.fullName,
            c.email,
            c.contactNo,
            c.gender,
            c.city,
            c.address
        ];

        values.forEach(value => {
            const cell = document.createElement("td");
            cell.textContent = value ?? "";
            row.appendChild(cell);
        });

        // Actions column
        const actionsCell = document.createElement("td");

        const editButton = document.createElement("button");
        editButton.textContent = "✏️ Edit";
        editButton.addEventListener("click", () => {
            editCustomer(c.customerId);
        });

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "🗑️ Delete";
        deleteButton.addEventListener("click", () => {
            deleteCustomer(c.customerId);
        });

        actionsCell.appendChild(editButton);
        actionsCell.appendChild(deleteButton);
        row.appendChild(actionsCell);

        body.appendChild(row);
    });

    message.textContent =
        `✅ ${filteredCustomers.length} customer(s) found.`;
}

// EDIT CUSTOMER
async function editCustomer(id) {
    try {
        const getResponse = await fetch(`/api/customers/${id}`);

        if (!getResponse.ok) {
            throw new Error("Unable to fetch customer details");
        }

        const customer = await getResponse.json();

        const fullName = prompt(
            "Enter Full Name:", customer.fullName ?? ""
        );
        if (fullName === null || fullName.trim() === "") return;

        const email = prompt(
            "Enter Email:", customer.email ?? ""
        );
        if (email === null || email.trim() === "") return;

        const contactNo = prompt(
            "Enter Contact Number:", customer.contactNo ?? ""
        );
        if (contactNo === null || contactNo.trim() === "") return;

        const city = prompt(
            "Enter City:", customer.city ?? ""
        );
        if (city === null || city.trim() === "") return;

        const address = prompt(
            "Enter Address:", customer.address ?? ""
        );
        if (address === null || address.trim() === "") return;

        const updatedCustomer = {
            fullName: fullName.trim(),
            dateOfBirth: customer.dateOfBirth,
            gender: customer.gender,
            email: email.trim(),
            contactNo: contactNo.trim(),
            address: address.trim(),
            city: city.trim()
        };

        const updateResponse = await fetch(`/api/customers/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedCustomer)
        });

        if (!updateResponse.ok) {
            throw new Error("Failed to update customer");
        }

        alert("✅ Customer updated successfully!");
        await loadCustomers();

    } catch (error) {
        console.error("Update Customer Error:", error);
        alert("❌ Unable to update customer. Check the backend.");
    }
}

// DELETE CUSTOMER
async function deleteCustomer(id) {
    const confirmed = confirm(
        "Are you sure you want to delete this customer?"
    );

    if (!confirmed) return;

    try {
        const response = await fetch(`/api/customers/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("Delete failed");
        }

        alert("✅ Customer deleted successfully!");
        await loadCustomers();

    } catch (error) {
        console.error("Delete Customer Error:", error);
        alert(
            "❌ Unable to delete customer. The customer may still have a linked account."
        );
    }
}

// INITIALIZE PAGE
document.addEventListener("DOMContentLoaded", () => {
    loadCustomerSearch();
    loadCustomers();
});
