package com.dezzy.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dezzy.chatbot.BuildConfig
import com.dezzy.chatbot.R
import com.dezzy.chatbot.ui.theme.ModelChatBackground
import com.dezzy.chatbot.ui.theme.UserChatBackground

@Composable
fun ChatScreen(modifier: Modifier =Modifier, viewModel: ChatViewModel) {
    Column(modifier=modifier) {
        TopAppBar()
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = viewModel.messageList,
        )
        MessageInputField(onMessageSend = {
            viewModel.sendMessage(it)
        })
    }
}

@Composable
fun TopAppBar() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<Message>) {
    if (messageList.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.round_question_answer_24),
                contentDescription = "Icon",
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(text = stringResource(id = R.string.ask_anything), fontSize = 22.sp)
        }
    } else {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageItem(message = it)
            }
        }
    }
}

@Composable
fun MessageItem (message: Message) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .align(if (message.isUser) Alignment.BottomEnd else Alignment.BottomStart)
                .padding(
                    start = if (message.isUser) dimensionResource(id = R.dimen.padding_large) else dimensionResource(
                        id = R.dimen.padding_small
                    ),
                    end = if (message.isUser) dimensionResource(id = R.dimen.padding_small) else dimensionResource(
                        id = R.dimen.padding_large
                    ),
                    top = 8.dp,
                    bottom = 8.dp
                )
                .clip(RoundedCornerShape(48f))
                .background(if (message.isUser) UserChatBackground else ModelChatBackground)
                .padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                SelectionContainer {
                    Text(
                        text = message.message,
                        color = Color.White,
                        fontWeight = FontWeight.W500
                    )
                }
            }
        }
    }
}

@Composable
fun MessageInputField(
    onMessageSend: (String) -> Unit
) {

    var message by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
            },
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = {
            if (message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }

        }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = stringResource(id = R.string.send_message)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    val chatViewModel = ChatViewModel()
    ChatScreen(viewModel = chatViewModel)
}