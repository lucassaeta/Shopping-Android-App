package com.example.practicafinalmoviles.clases

class Product(var productId: Int?=null, var name: String?=null, var description: String?=null, var price: Int?=null, var rating: Double?=null, var stock: Int?=null, var brand: String?=null, var image: String?=null) {
    override fun toString(): String {
        return "Product(id=$productId, title=$name, description=$description, price=$price, rating=$rating, stock=$stock, brand=$brand, image=$image)"
    }
}