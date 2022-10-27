package com.mobidal.pharmacynamoune.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.db.entity.PictureEntity
import com.mobidal.pharmacynamoune.db.entity.PrimaryCategoryEntity
import com.mobidal.pharmacynamoune.db.entity.ProductEntity
import com.mobidal.pharmacynamoune.db.entity.SecondaryCategoryEntity
import com.mobidal.pharmacynamoune.executor.AppExecutors.Companion.instance

class TestActivity : AppCompatActivity() {
    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // Initials AppDatabase
        mDb = getInstance(applicationContext)
        instance!!.diskIO().execute { // Primary Category
            val primaryCategoryEntity = PrimaryCategoryEntity()
            primaryCategoryEntity.name = "Electronics"
            val pcId = mDb!!.primaryCategoryDao()!!.insert(primaryCategoryEntity)

            // Secondary Category
            val secondaryCategoryEntity = SecondaryCategoryEntity()
            secondaryCategoryEntity.name = "Laptop"
            secondaryCategoryEntity.offerText = "Get the best deal today"
            secondaryCategoryEntity.pictureUrl =
                "https://img.lovepik.com/free-png/20210926/lovepik-laptop-icon-free-vector-illustration-material-png-image_401496506_wh1200.png"
            secondaryCategoryEntity.primaryCategoryId = pcId
            val secondaryCategoryEntity2 = SecondaryCategoryEntity()
            secondaryCategoryEntity2.name = "Phones"
            secondaryCategoryEntity2.offerText = "Start calling today "
            secondaryCategoryEntity2.pictureUrl =
                "https://www.seekpng.com/png/detail/97-972664_image-transparent-background-white-phone-icon.png"
            secondaryCategoryEntity2.primaryCategoryId = pcId
            val secondaryCategoryEntity3 = SecondaryCategoryEntity()
            secondaryCategoryEntity3.name = "Accessories"
            secondaryCategoryEntity3.offerText = "Enhance your User Experience "
            secondaryCategoryEntity3.pictureUrl =
                "https://cdn.iconscout.com/icon/premium/png-256-thumb/computer-accessories-3486347-2915291.png"
            secondaryCategoryEntity3.primaryCategoryId = pcId
            val secondaryCategoryEntity4 = SecondaryCategoryEntity()
            secondaryCategoryEntity4.name = "Gaming"
            secondaryCategoryEntity4.offerText = "Relax with style "
            secondaryCategoryEntity4.pictureUrl =
                "https://cdn.dribbble.com/users/1787323/screenshots/12057610/media/8e7e8e869bf389fb727ac393c42e6b27.png?compress=1&resize=400x300&vertical=top"
            secondaryCategoryEntity4.primaryCategoryId = pcId
            val scId = mDb!!.secondaryCategoryDao()!!.insert(secondaryCategoryEntity)
            val scId2 = mDb!!.secondaryCategoryDao()!!.insert(secondaryCategoryEntity2)
            val scId3 = mDb!!.secondaryCategoryDao()!!.insert(secondaryCategoryEntity3)
            val scId4 = mDb!!.secondaryCategoryDao()!!.insert(secondaryCategoryEntity4)

            // Products
            val product = ProductEntity()
            val product1 = ProductEntity()
            val product2 = ProductEntity()
            val product3 = ProductEntity()
            val product9 = ProductEntity()
            product.mark = "Apple"
            product.name = "Macbook Pro"
            product.description = "Macbook Pro I5-8000"
            product.pictureUrl =
                "https://cdn.shopify.com/s/files/1/0575/3628/2777/products/Untitled_500x500px_-2022-04-14T110450.176_ee91927d-4a23-4a21-bee6-ce9d815f8b2d_500x.png?v=1653146854"
            product.secondaryCategoryId = scId
            product.viewNumber = 105
            product.price = 1500
            product1.mark = "Lenovo"
            product1.name = "Lenovo Legion  "
            product1.description =
                "Legion 5 Gaming Laptop, 15.6\" FHD Display, AMD Ryzen 7 5800H, 16GB RAM, 1TB SSD Storage"
            product1.pictureUrl = "https://m.media-amazon.com/images/I/612jeGFw-sL._AC_SX425_.jpg"
            product1.secondaryCategoryId = scId
            product1.viewNumber = 102
            product1.price = 3000
            product2.mark = "HP "
            product2.name = "HP Stream Laptop "
            product2.description = "Intel Celeron N4000 Processor, 4GB RAM"
            product2.pictureUrl = "https://m.media-amazon.com/images/I/61dwNL8CXOL._AC_SX425_.jpg"
            product2.secondaryCategoryId = scId
            product2.viewNumber = 320
            product2.price = 1500
            product3.mark = "MSI "
            product3.name = "MSI GF65 "
            product3.description = "MSI GF65 Thin 9SD-252 15. 6\" 120Hz Gaming Laptop Intel Core"
            product3.pictureUrl = "https://m.media-amazon.com/images/I/71pbdMZHSuL._AC_SX679_.jpg"
            product3.secondaryCategoryId = scId
            product3.viewNumber = 100
            product3.price = 2500
            product9.mark = "Dell "
            product9.name = "Alienware M15 R6 Gaming Laptop "
            product9.description =
                "15.6-inch FHD (1920 x 1080) 1ms 360Hz Display, Intel Core i7-11800H, 32GB DDR4 RAM, 1TB SSD, NVIDIA RTX 3070 8GB Graphics, Windows 11 Home - Black"
            product9.pictureUrl =
                "https://m.media-amazon.com/images/I/71J1lHBTo3L.__AC_SX300_SY300_QL70_FMwebp_.jpg"
            product9.secondaryCategoryId = scId
            product9.viewNumber = 500
            product9.price = 3500
            val pId = mDb!!.productDao()!!.insert(product)!!
            val p1Id = mDb!!.productDao()!!.insert(product1)!!
            val p2Id = mDb!!.productDao()!!.insert(product2)!!
            val p3Id = mDb!!.productDao()!!.insert(product3)!!
            val p9Id = mDb!!.productDao()!!.insert(product9)!!

            // Product 0
            val pictureEntity = PictureEntity()
            pictureEntity.productId = pId
            pictureEntity.pictureUrl =
                "https://cdn.shopify.com/s/files/1/0575/3628/2777/products/Untitled_500x500px_-2022-04-14T110450.176_ee91927d-4a23-4a21-bee6-ce9d815f8b2d_500x.png?v=1653146854"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = pId
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71WtFY52CeL._AC_SX466_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = pId
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61qFGdy6UVL._AC_SX466_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            // Product 1
            pictureEntity.productId = p1Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/612jeGFw-sL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p1Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61z4Uz9iAfL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p1Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61pm-c1qA2L._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 2
            pictureEntity.productId = p2Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61dwNL8CXOL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p2Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/51y0gOir+3L._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p2Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61AIbOG3NKL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            //Picture 3
            pictureEntity.productId = p3Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71pbdMZHSuL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p3Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71xr-ASX2BL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p3Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/81+7yYWduEL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 9
            pictureEntity.productId = p9Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71J1lHBTo3L.__AC_SX300_SY300_QL70_FMwebp_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p9Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61q+SyrP0tS._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p9Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61qDFnKjDbS._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            val product4 = ProductEntity()
            val product5 = ProductEntity()
            val product6 = ProductEntity()
            val product7 = ProductEntity()
            val product8 = ProductEntity()
            product4.mark = "Samsung "
            product4.name = "Galaxy A12"
            product4.description =
                "(32GB, 3GB) 6.5\" HD+, Quad Camera, 5000mAh Battery, Global 4G Volte"
            product4.pictureUrl = "https://m.media-amazon.com/images/I/81t8kQP6OQL._AC_SX679_.jpg"
            product4.secondaryCategoryId = scId2
            product4.viewNumber = 50
            product4.price = 800
            product5.mark = "Samsung "
            product5.name = "Galaxy S10"
            product5.description = "Prism White"
            product5.pictureUrl = "https://m.media-amazon.com/images/I/61Kdfg4hZPL._AC_SY741_.jpg"
            product5.secondaryCategoryId = scId2
            product5.viewNumber = 150
            product5.price = 550
            product6.mark = "Apple "
            product6.name = "Apple iPhone 13 Pro Max"
            product6.description = "128GB, Sierra Blue"
            product6.pictureUrl = "https://m.media-amazon.com/images/I/61FZC+6hDFL._AC_SX679_.jpg"
            product6.secondaryCategoryId = scId2
            product6.viewNumber = 200
            product6.price = 800
            product7.mark = "SAMSUNG  "
            product7.name = "Galaxy Z Fold "
            product7.description = " 512GB, Flex Mode,Gray Green"
            product7.pictureUrl = "https://m.media-amazon.com/images/I/71a87teD1KL._AC_SX425_.jpg"
            product7.secondaryCategoryId = scId2
            product7.viewNumber = 300
            product7.price = 1000
            product8.mark = "SAMSUNG  "
            product8.name = "Galaxy S22"
            product8.description =
                " 512GB, 8K Camera & Video, Brightest Display Screen, S Pen, Long Battery Life"
            product8.pictureUrl = "https://m.media-amazon.com/images/I/61U6oC65TTL._AC_SX425_.jpg"
            product8.secondaryCategoryId = scId2
            product8.viewNumber = 100
            product8.price = 850
            val p4Id = mDb!!.productDao()!!.insert(product4)!!
            val p5Id = mDb!!.productDao()!!.insert(product5)!!
            val p6Id = mDb!!.productDao()!!.insert(product6)!!
            val p7Id = mDb!!.productDao()!!.insert(product7)!!
            val p8Id = mDb!!.productDao()!!.insert(product8)!!
            //Picture 4
            pictureEntity.productId = p4Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/81t8kQP6OQL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p4Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/51LVTBV6tjL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p4Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/81PNk91uNiL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 5
            pictureEntity.productId = p5Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61Kdfg4hZPL._AC_SY741_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p5Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/51c94MUES9L._AC_SY741_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p5Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/51qrh7MGdML._AC_SY741_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 6
            pictureEntity.productId = p6Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61FZC+6hDFL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p6Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71dRq3DaaYL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p6Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/81f9uXE7xFL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 7
            pictureEntity.productId = p7Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71a87teD1KL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p7Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71GO79IeU8L._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p7Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71rtLTkq7PL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 8
            pictureEntity.productId = p8Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61U6oC65TTL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p8Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61ey7V6Mm6L._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p8Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71p1SFyK6RL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            val product10 = ProductEntity()
            val product11 = ProductEntity()
            val product12 = ProductEntity()
            val product13 = ProductEntity()
            val product14 = ProductEntity()
            product10.mark = "Ykoee "
            product10.name = "Laptop Stand"
            product10.description =
                "Adjustable Portable Laptop Holder for Desk, Heat-Vent Laptop Riser for MacBook Pro/Air, 11-16\" Notebook, Silve"
            product10.pictureUrl = "https://m.media-amazon.com/images/I/61UOSq7lF9L._AC_SX425_.jpg"
            product10.secondaryCategoryId = scId3
            product10.viewNumber = 100
            product10.price = 100
            product11.mark = "Seagate  "
            product11.name = " External Hard Drive"
            product11.description =
                "Seagate Portable 1TB External Hard Drive HDD – USB 3.0 for PC, Mac, PlayStation, & Xbox, 1-Year Rescue Service (STGX1000400) , Black"
            product11.pictureUrl =
                "https://m.media-amazon.com/images/I/81tjLksKixL.__AC_SX300_SY300_QL70_FMwebp_.jpg"
            product11.secondaryCategoryId = scId3
            product11.viewNumber = 100
            product11.price = 200
            product12.mark = "Ytonet"
            product12.name = "Laptop Case"
            product12.description =
                "15.6 inch TSA Laptop Sleeve Water Resistant Durable Computer Carrying Case Compatible for HP, Dell, Lenovo, Asus Notebook, Gifts for Men Women, Grey"
            product12.pictureUrl =
                "https://m.media-amazon.com/images/I/81On3MEFDRL.__AC_SY300_SX300_QL70_FMwebp_.jpg"
            product12.secondaryCategoryId = scId3
            product12.viewNumber = 80
            product12.price = 50
            product13.mark = "RaceGT "
            product13.name = "Gaming Keyboard and Mouse"
            product13.description =
                "Gaming Keyboard and Mouse,RaceGT USB Backlit 104 Keys Wired Keyboard Gaming Mechanical Feeling ，7 Buttons 6400 DPI Gaming Mouse，Gaming Accessories Compatible with PC Laptop"
            product13.pictureUrl =
                "https://m.media-amazon.com/images/I/71D1fZFDiIL.__AC_SX300_SY300_QL70_FMwebp_.jpg"
            product13.secondaryCategoryId = scId3
            product13.viewNumber = 110
            product13.price = 25
            product14.mark = "SteelSeries "
            product14.name = " Wireless Multi-System Gaming Headset"
            product14.description =
                "SteelSeries Arctis Nova Pro Wireless Multi-System Gaming Headset - Premium Hi-Fi Drivers - Active Noise Cancellation - Infinity Power System - ClearCast Gen 2 Mic - PC, PS5, PS4, Switch, Mobile"
            product14.pictureUrl =
                "https://m.media-amazon.com/images/I/71fz1tjzJUL.__AC_SX300_SY300_QL70_FMwebp_.jpg"
            product14.secondaryCategoryId = scId3
            product14.viewNumber = 80
            product14.price = 120
            val p10Id = mDb!!.productDao()!!.insert(product10)!!
            val p11Id = mDb!!.productDao()!!.insert(product11)!!
            val p12Id = mDb!!.productDao()!!.insert(product12)!!
            val p13Id = mDb!!.productDao()!!.insert(product13)!!
            val p14Id = mDb!!.productDao()!!.insert(product14)!!

            //Picture 10
            pictureEntity.productId = p10Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61UOSq7lF9L._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p10Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61UOSq7lF9L._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p10Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61UOSq7lF9L._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 11
            pictureEntity.productId = p11Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/81tjLksKixL.__AC_SX300_SY300_QL70_FMwebp_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p11Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/91QqR55XhQL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p11Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/91rx2KE8YGL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 12
            pictureEntity.productId = p12Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/81On3MEFDRL.__AC_SY300_SX300_QL70_FMwebp_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p12Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71n7gMvkzkL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p12Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/81vfD-MVIDL._AC_SX425_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 13
            pictureEntity.productId = p13Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71D1fZFDiIL.__AC_SX300_SY300_QL70_FMwebp_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p13Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/712aZbF2imL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p13Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61mb+whpnwL._AC_SX679_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 14
            pictureEntity.productId = p14Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71fz1tjzJUL.__AC_SX300_SY300_QL70_FMwebp_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p14Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71fz1tjzJUL.__AC_SX300_SY300_QL70_FMwebp_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p14Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/71fz1tjzJUL.__AC_SX300_SY300_QL70_FMwebp_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            val product15 = ProductEntity()
            val product16 = ProductEntity()
            val product17 = ProductEntity()
            val product18 = ProductEntity()
            product15.mark = "Sony "
            product15.name = " PlayStation 4"
            product15.description = "PlayStation 4 Slim 1TB Console - Black (Renewed)"
            product15.pictureUrl = "https://m.media-amazon.com/images/I/71pFnHBrvgL._SX522_.jpg"
            product15.secondaryCategoryId = scId4
            product15.viewNumber = 1200
            product15.price = 800
            product16.mark = "Microsoft "
            product16.name = " Xbox One"
            product16.description =
                "USB 3.0 ,802.11n wireless with Wi-Fi Direct Blu-ray ,HDMI in/out"
            product16.pictureUrl = "https://m.media-amazon.com/images/I/51NzfmMfprL._SX466_.jpg"
            product16.secondaryCategoryId = scId4
            product16.viewNumber = 1200
            product16.price = 800
            product17.mark = "Nintendo "
            product17.name = "Nintendo Switch Lite Hand"
            product17.description =
                "Nintendo Switch Lite Hand-Held Gaming Console - Gray (HDH-001),Optimized for personal, handheld play, Nintendo Switch Lite is a small and light Nintendo Switch system" +
                        "Features a sleek, unibody design with fully integrated controls and a built in +Control Pad"
            product17.pictureUrl = "https://m.media-amazon.com/images/I/61Ygu+XErZL._SX522_.jpg"
            product17.secondaryCategoryId = scId4
            product17.viewNumber = 300
            product17.price = 500
            product18.mark = "HEYNOW  "
            product18.name = " HEYNOW Video Game Console"
            product18.description =
                "HEYNOW Video Game Console, Super Console X PRO Max, TV&Game System in 1, with 70000+ Games, Arcade Game Console 50+Emulators, for 4K TV HD/AV Output, Wireless 2.4G Controllers, For PS1/PSP/N64(256G)"
            product18.pictureUrl = "https://m.media-amazon.com/images/I/61aJE8ZCc7L._AC_SX569_.jpg"
            product18.secondaryCategoryId = scId4
            product18.viewNumber = 120
            product18.price = 1000
            val p15Id = mDb!!.productDao()!!.insert(product15)!!
            val p16Id = mDb!!.productDao()!!.insert(product16)!!
            val p17Id = mDb!!.productDao()!!.insert(product17)!!
            val p18Id = mDb!!.productDao()!!.insert(product18)!!

            //Picture 15
            pictureEntity.productId = p15Id
            pictureEntity.pictureUrl = "https://m.media-amazon.com/images/I/71pFnHBrvgL._SX522_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p15Id
            pictureEntity.pictureUrl = "https://m.media-amazon.com/images/I/71XrnwP7yjL._SX522_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p15Id
            pictureEntity.pictureUrl = "https://m.media-amazon.com/images/I/71n9+COCA8L._SX522_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 16
            pictureEntity.productId = p16Id
            pictureEntity.pictureUrl = "https://m.media-amazon.com/images/I/51NzfmMfprL._SX466_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p16Id
            pictureEntity.pictureUrl = "https://m.media-amazon.com/images/I/61yxBhMkNeL._SX466_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p16Id
            pictureEntity.pictureUrl = "https://m.media-amazon.com/images/I/6198FXoTAFL._SX466_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 17
            pictureEntity.productId = p17Id
            pictureEntity.pictureUrl = "https://m.media-amazon.com/images/I/61Ygu+XErZL._SX522_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p17Id
            pictureEntity.pictureUrl = "https://m.media-amazon.com/images/I/71sOKeiaeWL._SX522_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p17Id
            pictureEntity.pictureUrl = "https://m.media-amazon.com/images/I/8123IvL5f9L._SX522_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)

            //Picture 18
            pictureEntity.productId = p18Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61aJE8ZCc7L._AC_SX569_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p18Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61zPaJ4Gp5L._AC_SX569_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
            pictureEntity.productId = p18Id
            pictureEntity.pictureUrl =
                "https://m.media-amazon.com/images/I/61NBtLORkqL._AC_SX569_.jpg"
            mDb!!.pictureDao()!!.insert(pictureEntity)
        }
    }
}