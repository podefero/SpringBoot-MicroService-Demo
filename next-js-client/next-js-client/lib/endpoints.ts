import { OrderModel } from "./models/order";

export const postOrder = async (body: typeof OrderModel) => {
    try {
      const response = await fetch("http://localhost:8080/orders", {
        method: "POST",
        headers: {
          "Content-Type": "application/json", // Setting content type to JSON
        },
        body: JSON.stringify(body), // Convert the body string to JSON format
      });
  
      if (!response.ok) {
        // If the response is not OK, throw an error with the status
        throw new Error(`Error: ${response.statusText}`);
      }
  
      // Return the response in JSON format
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error posting order:", error);
      throw error; // Re-throw error to handle it elsewhere if needed
    }
  };
  