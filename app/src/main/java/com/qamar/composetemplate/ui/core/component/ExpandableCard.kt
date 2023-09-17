
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.ui.theme.BgColor
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.ColorSecondary
import com.qamar.composetemplate.ui.theme.boldStyle
import com.qamar.composetemplate.ui.theme.regularStyle
import com.qamar.composetemplate.util.getResource
import com.qamar.composetemplate.util.noRippleClickable
import com.selsela.cpapp.R

@Composable
fun ExpandableCard(
    isOpenByDefault: Boolean = false,
    title: String,
    hasItem: Boolean = true,
    padding: Dp = 15.dp,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {

    var expanded by remember { mutableStateOf(isOpenByDefault) }

    val rotation by animateFloatAsState(
        targetValue =
        if (expanded.not())
            0f
        else 180f, label = ""
    )
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 35.dp)
            .animateContentSize()
            .noRippleClickable {
                if (hasItem)
                expanded = !expanded
                else onClick()
            },
        colors = CardDefaults.cardColors(containerColor = BgColor)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(horizontal = padding),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 35.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = if (expanded) boldStyle else regularStyle,
                        fontSize = 13.sp,
                        color = ColorPrimaryDark
                    )

                    if (hasItem) {
                        Spacer(modifier = Modifier.weight(1f))

                        Image(
                            painter = R.drawable.downarrow.getResource(),
                            contentDescription = "down arrow",
                            modifier = Modifier.rotate(rotation)
                        )
                    }
                }

                if (expanded) {
                    Spacer(modifier = Modifier.height(9.dp))
                    content()
                    Spacer(modifier = Modifier.height(9.dp))

                }
            }
        }
    }
}

@Composable
fun ExpandableCard(
    isExpand: Boolean = false,
    title: String,
    icon: Int,
    padding: Dp = 19.dp,
    onExpand: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {

    val rotation by animateFloatAsState(
        targetValue =
        if (isExpand.not())
            if (Configurations.appLocal.value == "en") -90f else 90f
        else 180f, label = ""
    )
    Card(
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 45.dp)
            .animateContentSize()
            .noRippleClickable {
                onExpand(!isExpand)
            },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(horizontal = padding),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        painter = icon.getResource(), contentDescription = "icon",
                        tint =  if (isExpand.not()) ColorSecondary else ColorPrimaryDark
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = title,
                        style = if (isExpand.not()) regularStyle else boldStyle,
                        fontSize = 12.sp,
                        color = ColorPrimaryDark
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = R.drawable.downarrow.getResource(),
                        contentDescription = "down arrow",
                        modifier = Modifier.rotate(rotation),
                        colorFilter = ColorFilter.tint(ColorSecondary)
                    )
                }

                if (isExpand) {
                    Spacer(modifier = Modifier.height(15.dp))
                    content()
                    Spacer(modifier = Modifier.height(15.dp))

                }
            }
        }
    }
}

@Composable
fun ExpandableCard(
    isOpenByDefault: Boolean = false,
    title: String,
    onExpand: (Boolean) -> Unit
) {

    var expanded by remember { mutableStateOf(isOpenByDefault) }

    val rotation by animateFloatAsState(
        targetValue =
        if (expanded.not())
            0f
        else 180f
    )
    Card(
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .animateContentSize()
            .clickable {
                expanded = !expanded
                onExpand(expanded)
            },
        border = BorderStroke(
            width = 1.dp,
            color = BgColor,
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 19.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = regularStyle,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = R.drawable.downarrow.getResource(),
                        contentDescription = "down arrow",
                        modifier = Modifier.rotate(rotation)
                    )
                }
            }
        }
    }
}