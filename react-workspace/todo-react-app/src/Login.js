import React from "react";
import { Container, Grid, Typography, TextField, Button, Link } from "@material-ui/core";
import { signin } from "./ApiService";
import { API_BASE_URL } from "./api-config";

function Login() {
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const username = data.get("username");
        const password = data.get("password");

        signin({ username: username, password: password });
    };

    const handleSocialLogin = (provider) => {
        window.location.href = API_BASE_URL + "/auth/authorize/" + provider
    }

    return (
        <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Typography component="h1" variant="h5">
                        로그인
                    </Typography>
                </Grid>
            </Grid>
            <form noValidate onSubmit={handleSubmit}>
                {" "}
                {/* submit 버튼을 누르면 handleSubmit이 실행됨. */}
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField 
                            variant="outlined"
                            required
                            fullWidth
                            id="username"
                            label="아이디"
                            name="username"
                            autoComplete="username"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField 
                            variant="outlined"
                            required
                            fullWidth
                            name="password"
                            label="패스워드"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button type="submit" fullWidth variant="contained" color="primary">
                            로그인
                        </Button>
                    </Grid>
                    <Grid item xs={12}>
                        <Button fullWidth variant="contained" onClick={() => handleSocialLogin("github")}>
                            깃허브로 로그인하기
                        </Button>
                    </Grid>
                </Grid>
                <Grid container justify="flex-end">
                    <Grid item>
                        <Link href="/signup" variant="body2">
                            계정이 없으시면 회원가입을 해주세요
                        </Link>
                    </Grid>
                </Grid>
            </form>
        </Container>
    );
};

export default Login;