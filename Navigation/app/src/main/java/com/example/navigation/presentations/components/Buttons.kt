package com.example.navigation.presentations.components

@Composable
fun NormalButton(
    title: String,
    onClick: (() -> Unit)? = null
){
    if (onClick == null){
        Button(
            onClick = {
                println("Como me quiero ir por favor")
            }
        ) {
            Text(title)
        }
        return
    }
    Button(
        onClick = onClick
    ) {
        Text(title)
    }
}

@Composable
fun CustomOutlinedButton(){
    OutlinedButton(
        onClick = {}
    ) {
        Text("Outlined")
    }
}

@Composable
fun CustomIconButton(){
    IconButton(
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = ""
        )
    }
}

@Composable
fun CustomFloatingActionButton(){
    FloatingActionButton(
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = ""
        )
    }
}