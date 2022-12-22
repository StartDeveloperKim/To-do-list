import "./App.css";
import Todo from "./Todo";
import React, { useEffect, useState } from "react";
import { Container, List, Paper, Grid, Button, AppBar, Toolbar, Typography, CircularProgress, } from "@material-ui/core";
import AddTodo from "./AddTodo";
import { call, signout } from "./ApiService";

function App() {
  // Todo 배열
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    call("/todo", "GET", null).then((response) => {
      setItems(response.data); 
      setLoading(false)
    });
  }, []);

  const addItem = (item) => {
    call("/todo", "POST", item)
    .then((response) => setItems(response.data));
  };

  const deleteItem = (item) => {
    call("/todo", "DELETE", item)
    .then((response) => setItems(response.data));
  };

  const editItem = (item) => {
    call("/todo", "PUT", item)
    .then((response) => setItems(response.data));
  };

  // 네비게이션 Bar
  let navigationBar = (
    <AppBar position="static">
      <Toolbar>
        <Grid jstifyContent="space-between" container>
          <Grid item>
            <Typography variant="h6">오늘의 할일</Typography>
          </Grid>
          <Grid item>
            <Button color="inherit" raised onClick={signout}>
              로그아웃
            </Button>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );
  

  // Todo 아이템
  let todoItems = items.length > 0 && (
    <Paper style={{ margin: 16 }}>
      <List>
        {items.map((item) => (
          <Todo item={item} key={item.id} deleteItem={deleteItem} editItem={editItem} />
        ))}
      </List>
    </Paper>
  );

  let todoListPage = (
    <div>
      {navigationBar}
      <Container maxWidth="md">
        <AddTodo addItem={addItem} />
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );

  let loadingPage = (
    <div>
      <CircularProgress />
      <h2>로딩 중..</h2>
    </div>
      
      );
  let content = loadingPage;

  if (!loading) {
    content = todoListPage;
  }

  return (
    <div className='App'>
      {content}
    </div>
  );
}

export default App;
