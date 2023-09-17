package com.qamar.composetemplate.ui.screens.category.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.data.remote.categories.model.category.Category
import com.qamar.composetemplate.data.remote.categories.model.products.Product
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.ui.core.component.AsyncImage
import com.qamar.composetemplate.ui.core.component.FavButton
import com.qamar.composetemplate.ui.core.component.RatingBar
import com.qamar.composetemplate.ui.core.component.TagLabel
import com.qamar.composetemplate.ui.theme.ColorPrimary
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.ColorSecondary
import com.qamar.composetemplate.ui.theme.OrangeColor
import com.qamar.composetemplate.ui.theme.boldStyle
import com.qamar.composetemplate.ui.theme.regularStyle
import com.selsela.cpapp.R


@Composable
fun ProductItem(
    product: Product,
    index: Int,
) {
    Row(Modifier.fillMaxWidth()) {
        if (index % 2 == 0) {
            Spacer(modifier = Modifier.width(16.dp))
        }
        Card(
            Modifier
                .weight(1f)
                .requiredHeight(271.dp)
                .background(color = Color.White, shape = RoundedCornerShape(7.dp))
                .padding(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(7.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(141.dp)
            ) {
                AsyncImage(
                    product.imageUrl,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
                if (product.discountRatio != 0)
                    TagLabel(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .scale(0.5f)
                            .offset(x = (50).dp),
                        "${product.discountRatio} ${if (product.isDiscountPercentage) "%" else Configurations.Currency}",
                    )
            }

            Text(
                text = product.category.name.ifEmpty {
                    product.brand?.name ?: ""
                },
                style = boldStyle,
                color = ColorSecondary,
                fontSize = 8.sp,
                minLines = 1,
                maxLines = 1,
            )
            Text(
                text = product.name,
                style = regularStyle,
                color = ColorPrimaryDark,
                fontSize = 11.sp,
                minLines = 2,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )
            RatingBar(
                rating = product.rate,
                space = 1.2.dp,
                imageVectorEmpty = ImageVector.vectorResource(R.drawable.starempty),
                imageVectorFFilled = ImageVector.vectorResource(R.drawable.startfill),
                animationEnabled = true,
                gestureEnabled = false,
                itemSize = 6.dp,
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp, top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${product.sellPrice}",
                        style = boldStyle,
                        fontSize = 10.sp,
                        color = OrangeColor,
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = Configurations.Currency,
                        style = boldStyle,
                        fontSize = 10.sp,
                        color = OrangeColor,
                        textAlign = TextAlign.Start,
                    )
                    if (product.discountRatio != 0) {
                        Text(
                            text = "${product.price}",
                            style = boldStyle,
                            color = ColorSecondary,
                            textAlign = TextAlign.Start,
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 8.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                }
                if (User.isLoggedIn.value) {
                    FavButton(
                        product.inFavorite == 1
                    ) {
                        // onFav
                    }
                }

            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(26.dp)
                    )
                    .border(
                        1.dp, if (product.quantity != 0)
                            ColorPrimary else OrangeColor,
                        RoundedCornerShape(26.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        id = R.string.addToCart
                    ),
                    style = boldStyle,
                    fontSize = 10.sp,
                    color = ColorPrimary
                )
            }
        }
        if (index % 2 == 1) {
            Spacer(modifier = Modifier.width(16.dp))
        }
    }


}


@Preview
@Composable
fun ProductItemPreview(){
    ProductItem(
        product = Product(
            category = Category(
                id = 1877,
                imageUrl = "https://search.yahoo.com/search?p=ancillae",
                name = "Malinda Beasley",
                productsCount = 4290,
                childs = listOf(),
                brands = listOf()
            ),
            brand = null,
            discountRatio = 6490,
            discountType = null,
            id = 5971,
            ratesCount = 6101,
            imageUrl = "https://www.google.com/#q=alterum",
            image = "iudicabit",
            inFavorite = 1811,
            inFeatured = 5473,
            name = "Lacey Ramsey",
            sku = "et",
            description = "at",
            featured = "tincidunt",
            orderCount = 6974,
            price = 6.7,
            quantity = 7423,
            availableQuantity = 6352,
            rate = 8.9f,
            sellPrice = 10.11,
            properties = listOf(),
            images = listOf(),
            rates = listOf(),
            hasVariant = 7543
        ), index = 3356
    )
}