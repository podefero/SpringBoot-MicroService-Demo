"use client"

import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import { OrderModel } from "@/lib/models/order"
import { postOrder } from "@/lib/endpoints"
import { z } from "zod"

import { Button } from "@/components/ui/button"
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"

const formSchema = z.object({
  name: z.string().min(2, {
    message: "Name must be at least 2 characters.",
  }),
  quantity: z.number().min(0, {
    message: "Quantity must be greater then 0.",
  }),
  category: z.string(),  
})



export function ProfileForm() {
  // ...
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: OrderModel,
  })

  async function onSubmit(values: z.infer<typeof formSchema>) {
    // Log the form values (for debugging or testing)
    console.log(values)

    try {
      // Call postOrder with form values
      const response = await postOrder(values) // pass the form data to postOrder
      console.log("Order response:", response) // Handle the response from postOrder
    } catch (error) {
      console.error("Error submitting order:", error)
    }
  }

  return (
    <div className="max-w-lg mx-auto p-4 bg-white rounded-lg shadow-md">
    <Form {...form}>
    <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
      {/* Name Field */}
      <FormField
        control={form.control}
        name="name"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Name</FormLabel>
            <FormControl>
              <Input placeholder="Product Name" {...field} />
            </FormControl>
            <FormDescription>
              Enter the name of the product.
            </FormDescription>
            <FormMessage />
          </FormItem>
        )}
      />

      {/* Quantity Field */}
      <FormField
        control={form.control}
        name="quantity"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Quantity</FormLabel>
            <FormControl>
              <Input type="number" placeholder="Quantity" {...field} />
            </FormControl>
            <FormDescription>
              Enter the quantity of the product.
            </FormDescription>
            <FormMessage />
          </FormItem>
        )}
      />

      {/* Category Field */}
      <FormField
        control={form.control}
        name="category"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Category</FormLabel>
            <FormControl>
              <Input placeholder="Category" {...field} />
            </FormControl>
            <FormDescription>
              Enter the product category.
            </FormDescription>
            <FormMessage />
          </FormItem>
        )}
      />

      {/* Submit Button */}
      <Button type="submit">Submit</Button>
    </form>
</Form>
</div>
  )
}
