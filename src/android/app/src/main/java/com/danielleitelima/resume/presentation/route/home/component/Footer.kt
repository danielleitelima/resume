package com.danielleitelima.resume.presentation.route.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.R
import com.danielleitelima.resume.presentation.foundation.launchUrl
import com.danielleitelima.resume.presentation.foundation.mailTo

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    emailAddress: String,
    linkedinUrl: String,
    githubUrl: String
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(top = 24.dp, bottom = 32.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val title = stringResource(R.string.home_footer_title)
        val themeColor = MaterialTheme.colorScheme.surfaceContainer.toArgb()

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FooterButton(icon = Icons.Outlined.MailOutline){
                context.mailTo(
                    email = emailAddress,
                    subject = title
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            FooterButton(painter = painterResource(id = R.drawable.ic_linkedin)){
                context.launchUrl(
                    url = linkedinUrl,
                    themeColor = themeColor
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            FooterButton(painter = painterResource(id = R.drawable.ic_github)){
                context.launchUrl(
                    url = githubUrl,
                    themeColor = themeColor
                )
            }
        }
    }
}

@Preview
@Composable
fun FooterPreview() {
    Footer(
        emailAddress = "limaleite.daniel@gmail.com",
        linkedinUrl = "https://www.linkedin.com/in/danielleitelima/",
        githubUrl = "https://github.com/danielleitelima/resume"
    )
}