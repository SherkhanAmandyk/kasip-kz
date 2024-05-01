package kz.kasip.order.ui.createproject

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import kotlinx.coroutines.flow.update
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.designcore.ButtonUiState
import kz.kasip.designcore.KasipDialog
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.RubricsList
import kz.kasip.designcore.add_the_cost
import kz.kasip.designcore.cost
import kz.kasip.designcore.create_project
import kz.kasip.designcore.detail_description_of_project
import kz.kasip.designcore.project_name
import kz.kasip.designcore.theme.DialogBackground
import kz.kasip.designcore.theme.PrimaryBackgroundGreen
import kz.kasip.designcore.write_the_name
import kz.kasip.designcore.write_the_namedescribe_your_project_requirements_deadlines_and_criteria_in_detail

@OptIn(ExperimentalMaterial3Api::class)
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
        val rubrics by viewModel.rubricsFlow.collectAsState()
        val selectedSubrubric by viewModel.selectedSubrubricFlow.collectAsState()
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val selectSubrubric by viewModel.selectSubrubricFlow.collectAsState()

        if (selectSubrubric) {
            ModalBottomSheet(
                modifier = Modifier.padding(top = 32.dp),
                onDismissRequest = {
                    viewModel.invalidateStates()
                },
                sheetState = sheetState
            ) {
                RubricsList(
                    rubrics = rubrics,
                    selectedSubrubricIds = selectedSubrubric?.let { listOf(it.id ?: "") }
                        ?: emptyList(),
                    onSubrubricToggle = {
                        viewModel.onSubrubricSelected(it)
                        viewModel.invalidateStates()
                    }
                )
            }
        }
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = lang[create_project]?:"",
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
                            text = lang[project_name] ?: "",
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
                                    Text(text = lang[write_the_name] ?: "")
                                }
                            )
                        }
                    }
                    Column {
                        Text(
                            text = lang[detail_description_of_project] ?: "",
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
                                        text = lang[write_the_namedescribe_your_project_requirements_deadlines_and_criteria_in_detail]
                                            ?: "",
                                        fontSize =
                                        12.sp
                                    )
                                },
                                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = lang[kz.kasip.designcore.rubrics] ?: "",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = TextFieldDefaults.colors().unfocusedContainerColor,
                            shape = TextFieldDefaults.shape,
                            onClick = {
                                viewModel.selectSubrubricFlow.update { true }
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = selectedSubrubric?.let { it.name } ?: "Write rubric"
                            )
                        }
                    }
                    Column {
                        Text(
                            text = lang[cost] ?: "",
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
                                    Text(text = lang[add_the_cost] ?: "")
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
                        Text(text = lang[create_project] ?: "")
                    }
                }
            }
        }
    }
    val isCreated by viewModel.isCreatedFlow.collectAsState()
    if (isCreated) {
        onBack()
        viewModel.invalidateStates()
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