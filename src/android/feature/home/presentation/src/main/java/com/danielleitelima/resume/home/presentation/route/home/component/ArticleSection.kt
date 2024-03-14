
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielleitelima.resume.home.domain.model.Article
import com.danielleitelima.resume.home.presentation.R
import com.danielleitelima.resume.home.presentation.route.home.component.ArticleItem
import com.danielleitelima.resume.home.presentation.route.home.component.SeeMoreButton

@Composable
fun ArticleSection(
    articles: List<Article>,
    onSeeMore: () -> Unit = {},
){
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.home_article_section_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(R.string.home_article_section_description),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            articles.forEach { article ->
                ArticleItem(
                    title = article.title,
                    description = article.description,
                    imageUrl = article.imageUrl,
                    label = article.label,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
        if (articles.size > 10) {
            Spacer(modifier = Modifier.height(24.dp))
            SeeMoreButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 24.dp),
            ){
                onSeeMore()
            }
        }
    }
}

@Preview
@Composable
private fun ArticleSectionPreview() {
    val articles = listOf(
        Article(
            title = "Kotlin",
            description = "Kotlin is a cross-platform, statically typed, general-purpose programming language with type inference.",
            imageUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            label = "Kotlin",
        ),
        Article(
            title = "Java",
            description = "Java is a class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.",
            imageUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            label = "Java",
        ),
        Article(
            title = "Python",
            description = "Python is an interpreted high-level general-purpose programming language.",
            imageUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            label = "Python",
        ),
    )
    ArticleSection(
        articles = articles,
    )
}
