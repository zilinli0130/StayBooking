/**
 * Copyright (c) 2023
 *
 * @summary Implementation of LoginPage.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// React imports
import React from "react";

// Antd imports
import { Form, Button, Input, Space, Checkbox, message } from "antd";
import { UserOutlined } from "@ant-design/icons";

// Project imports
import { login, register } from "../../utils/backend_utils";

class LoginPage extends React.Component {

    formRef = React.createRef();
    state = {
        asHost: false,
        loading: false,
    };

    onFinish = () => {
        console.log("finish form");
    };

    handleLogin = async () => {

        const formInstance = this.formRef.current;
        try {
            await formInstance.validateFields();
        } catch (error) {
            return;
        }

        this.setState({
            loading: true,
        });

        try {
            const { asHost } = this.state;
            const response = await login(formInstance.getFieldsValue(true), this.state.asHost);
            this.props.handleLoginSuccess(response.token, asHost);
        } catch (error) {
            message.error(error.message);
        } finally {
            this.setState({
                loading: false,
            });
        }
    };

    handleRegister = async () => {
        const formInstance = this.formRef.current;
        try {
            await formInstance.validateFields();
        } catch (error) {
            return;
        }

        this.setState({
            loading: true,
        });

        try {
            await register(formInstance.getFieldsValue(true), this.state.asHost);
            message.success("Register Successfully");
        } catch (error) {
            message.error(error.message);
        } finally {
            this.setState({
                loading: false,
            });
        }
    };

    handleCheckboxOnChange = (e) => {
        this.setState({
            asHost: e.target.checked,
        });
    };

    render() {
        return (
          <div style={{ width: 500, margin: "20px auto" }}>
            <Form ref={this.formRef} onFinish={this.onFinish}>
              <Form.Item
                name="username"
                rules={[
                  {
                    required: true,
                    message: "Please input your username!",
                  },
                ]}
              >
                <Input
                  disabled={this.state.loading}
                  prefix={<UserOutlined className="site-form-item-icon" />}
                  placeholder="username"
                />
              </Form.Item>
              <Form.Item
                name="password"
                rules={[
                  {
                    required: true,
                    message: "Please input your password!",
                  },
                ]}
              >
                <Input.Password
                  disabled={this.state.loading}
                  placeholder="password"
                />
              </Form.Item>
            </Form>
            <Space>
              <Checkbox
                disabled={this.state.loading}
                checked={this.state.asHost}
                onChange={this.handleCheckboxOnChange}
              >
                As Host
              </Checkbox>
              <Button
                onClick={this.handleLogin}
                disabled={this.state.loading}
                shape="round"
                type="default"
              >
                Log in
              </Button>
              <Button
                onClick={this.handleRegister}
                disabled={this.state.loading}
                shape="round"
                type="default"
              >
                Register
              </Button>
            </Space>
          </div>
        );
      }
};

export default LoginPage;