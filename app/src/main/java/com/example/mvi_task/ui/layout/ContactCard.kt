package com.example.mvi_task.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mvi_task.R
import com.example.mvi_task.model.Contact

@Composable
fun ContactCard(contact: Contact, onClick :()->Unit){
    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),

        ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                   onClick()
                },
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(R.drawable.desha2),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                    .clip(
                        CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
            Column {

                Text(
                    text = contact.name,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                Text(text = "Phone: ${contact.phone}")
                Text(text = "Address: ${contact.address}")
                Text(text = "Email: ${contact.email}")
                Text(text = "time: ${contact.time}")
                Text(text = "Date: ${contact.date}")
            }
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)

        }
    }
}