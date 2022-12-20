import React, { useState } from "react";
import { ListItem, ListItemText, InputBase, Checkbox, ListItemSecondaryAction, IconButton } from "@material-ui/core";
import { DeleteOutlined } from "@material-ui/icons";

const Todo = (props) => {
    const [item, setItem] = useState(props.item);
    const [readOnly, setReadOnly] = useState(true);

    const turnOffReadOnly = () => {
        setReadOnly(false);
    }
    const turnOnReadOnly = (e) => {
        if (e.key === "Enter" && readOnly === false){
            setReadOnly(true);
            editItem(item);
        }
    }

    const deleteItem = props.deleteItem;
    const deleteEventHandler = () => {
        deleteItem(item);
    };

    const editItem = props.editItem;
    const editEventHandler = (e) => {
        setItem({...item, title: e.target.value});
    };

    // 체크박스
    const checkboxEventHandler = (e) => {
        item.done = e.target.checked;
        editItem(item);
    }

    return (
        <ListItem>
            <Checkbox checked={item.done} onChange={checkboxEventHandler} />
            <ListItemText>
                <InputBase 
                    inputProps={{ 
                        "aria-lable": "naked",
                        readOnly: readOnly
                    }}
                    onClick={turnOffReadOnly}
                    onKeyDown={turnOnReadOnly}
                    onChange={editEventHandler}
                    type="text"
                    id={item.id}
                    name={item.id}
                    value={item.title}
                    multiline={true}
                    fullWidth={true}
                />
            </ListItemText>
            <ListItemSecondaryAction>
                <IconButton aria-label="Delete Todo" onClick={deleteEventHandler}>
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    );
};

export default Todo;