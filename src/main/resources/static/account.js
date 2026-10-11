
document.getElementById("accountForm")
    .addEventListener("submit", async function (event) {

        event.preventDefault();

        const customerId =
            document.getElementById("customerId").value;

        // Account number backend automatically generate karel
        const account = {
            accountType:
            document.getElementById("accountType").value,

            balance:
                Number(document.getElementById("balance").value),

            branch:
            document.getElementById("branch").value,

            ifscCode:
            document.getElementById("ifscCode").value
        };

        console.log("Customer ID:", customerId);
        console.log("Account Data:", account);

        try {
            const response = await fetch(
                `/api/accounts/${customerId}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(account)
                }
            );

            const responseText = await response.text();

            console.log("Backend Response:", responseText);

            if (!response.ok) {
                throw new Error(responseText);
            }

            const data = JSON.parse(responseText);

            document.getElementById("accountMessage").textContent =
                "✅ Account Created Successfully! Account No: " +
                data.accountNo;

            alert(
                "Account Created Successfully! 🎉\nAccount No: " +
                data.accountNo
            );

            document.getElementById("accountForm").reset();

        } catch (error) {
            console.error("Account Error:", error);

            document.getElementById("accountMessage").textContent =
                "❌ Account Creation Failed";

            alert(
                "❌ Account Creation Failed\n\n" +
                error.message
            );
        }

    });
