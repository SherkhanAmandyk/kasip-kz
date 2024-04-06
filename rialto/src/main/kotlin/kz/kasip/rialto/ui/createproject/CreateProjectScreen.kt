package kz.kasip.rialto.ui.createproject

import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.designcore.ButtonUiState
import kz.kasip.designcore.KasipDialog
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.theme.DialogBackground
import kz.kasip.designcore.theme.PrimaryBackgroundGreen

@Composable
fun CreateProjectScreen(
    viewModel: CreateProjectViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val validation by viewModel.validationFlow.collectAsState()
    when (validation) {
        Validation.NoPrice -> "Price is not specified"
        Validation.NoProjectDescription -> "Project description is blank"
        Validation.NoProjectName -> "Project name is blank"
        Validation.OK -> null
    }?.let {
        KasipDialog(
            title = it,
            buttons = listOf(ButtonUiState(text = "Ok")),
            onDismissRequest = viewModel::invalidateStates,
        ) {
            viewModel.invalidateStates()
        }
    }
    Surface(
        color = DialogBackground
    ) {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = "Create project",
                    color = Color.White,
                    onBack = onBack
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(top = 32.dp)
                    .padding(horizontal = 18.dp)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column {
                        Text(
                            text = "Project name",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.elevatedCardColors().copy(
                                containerColor = Color.White
                            )
                        ) {
                            val offerText by viewModel.projectNameFlow.collectAsState()
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = offerText,
                                onValueChange = viewModel::onProjectNameChange,
                                label = {
                                    Text(text = "Write the name")
                                }
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "Detail description of project",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.elevatedCardColors().copy(
                                containerColor = Color.White
                            )
                        ) {
                            val offerText by viewModel.projectDescriptionFlow.collectAsState()
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = offerText,
                                onValueChange = viewModel::onProjectDescriptionChange,
                                label = {
                                    Text(
                                        text = "Write the nameDescribe your project requirements, deadlines and criteria in detail",
                                        fontSize = 12.sp
                                    )
                                },
                                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "Rubric",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.elevatedCardColors().copy(
                                containerColor = Color.White
                            )
                        ) {
                            val offerText by viewModel.rubricFlow.collectAsState()
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = offerText,
                                onValueChange = { },
                                label = {
                                    Text(text = "Write rubric")
                                }
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "Cost",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.elevatedCardColors().copy(
                                containerColor = Color.White
                            )
                        ) {
                            val offerText by viewModel.priceFlow.collectAsState()
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = offerText,
                                onValueChange = viewModel::onPriceChange,
                                label = {
                                    Text(text = "Add the cost")
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Decimal
                                )
                            )
                        }
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = viewModel::onCreateProject,
                        colors = ButtonDefaults.buttonColors()
                            .copy(containerColor = PrimaryBackgroundGreen)
                    ) {
                        Text(text = "Create project")
                    }
                }
            }
        }
    }
    val isCreated by viewModel.isCreatedFlow.collectAsState()
    if (isCreated) {
        onBack()
    }
}

@Preview
@Composable
fun PreviewCreateProjectScreen() {
    CreateProjectScreen(
        CreateProjectViewModel(
            DataStoreRepository(
                LocalContext.current.getSharedPreferences("", MODE_PRIVATE)
            )
        ),
        onBack = {}
    )
}