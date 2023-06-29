/**
 * Copyright (c) 2023
 *
 * @summary Implementation of UploadStay.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// React imports
import React from "react";

// Antd imports
import { Form, Input, InputNumber, Button } from "antd";

// Project imports
import { uploadStay } from "../../utils/backend_utils";

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};
  
class UploadStay extends React.Component {

    state = {
        loading: false,
    };

    fileInputRef = React.createRef();
    
    handleSubmit = async (values) => {
        const formData = new FormData();
        const {files} = this.fileInputRef.current;
        const { onDefaultKey } = this.props;

        if (files.length > 5) {
            // showWarning("Warning", "The maximum number of uploaded images is 5.");
            return;
        }

        for (let i = 0; i < files.length; i++) {
            formData.append("images", files[i]);
        }

        formData.append("name", values.name);
        formData.append("address", values.address);
        formData.append("description", values.description);
        formData.append("guest_number", values.guest_number);

        this.setState({
            loading: true,
        });

        try {
            await uploadStay(formData);
            // showSuccess("Confirmation", "The stay is uploaded successfully!");
            onDefaultKey();
           
        } catch (error) {
            // showError("Error!", "The stay is failed to upload...");
            
        } finally {
            this.setState({
                loading: false,
            });
        }
    };

    render() {
        return (
            <Form
                {...layout}
                name="nest-messages"
                onFinish={this.handleSubmit}
                style={{ maxWidth: 1000, margin: "auto" }}
                
            >
                <Form.Item
                    name="name"
                    label="Name"
                    rules={[{required: true}]}   
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    name="address"
                    label="Address"
                    rules={[{required: true}]}   
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    name="description"
                    label="Description"
                    rules={[{required: true}]}   
                >
                    <Input.TextArea autoSize={{minRows:2, maxRows:6}} />
                </Form.Item>

                <Form.Item
                    name="guest_number"
                    label="Guest Number"
                    rules={[{required: true, type: "number", min: 1}]}   
                >
                    <InputNumber />
                </Form.Item>

                <Form.Item 
                    name="picture" 
                    label="Picture" 
                    rules={[{ required: true }]}
                >
                    <input
                        type="file"
                        accept="image/png, image/jpeg"
                        ref={this.fileInputRef}
                        multiple={true}
                    />
                </Form.Item>

                <Form.Item 
                    wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
                    <Button 
                        type="primary" htmlType="submit" 
                        loading={this.state.loading}
                    >
                        Upload
                    </Button>
                </Form.Item>
            </Form>
        );
    };

};

export default UploadStay;