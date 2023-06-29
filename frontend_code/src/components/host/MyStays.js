/**
 * Copyright (c) 2023
 *
 * @summary Implementation of MyStays.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// Antd imports
import { message, List, Card, Image, Carousel, Row, Col } from "antd";
import { LeftCircleFilled, RightCircleFilled } from "@ant-design/icons";
import Text from "antd/lib/typography/Text";

// React imports
import React from "react";

// Project imports
import { getStaysByHost } from "../../utils/backend_utils";
import StayDetailInfoButton from "./StayDetailInfoButton";
import ViewReservationsButton from "./ViewReservationsButton";
import RemoveStayButton from "./RemoveStayButton";

class MyStays extends React.Component {

    state = {
        loading: false,
        data: [],
    };

    componentDidMount() {
        this.loadData();
    }

    loadData = async () => {

        this.setState({
            loading: true,
        });

        try {
            const response = await getStaysByHost();
            this.setState({
                data: response,
            });
        
        } catch(error) {
            message.error(error.message);

        } finally {
            this.setState({
                loading: false,
            });
        }
    };

    render() {
        return (
            <Row>
                <Col span={23}>
                            <List 
                            loading={this.state.loading}
                            grid={{
                                gutter: 16,
                                xs: 1,
                                sm: 3,
                                md: 3,
                                lg: 3,
                                xl: 4,
                                xxl: 4,
                            }}
                            dataSource={this.state.data}
                            renderItem={(item) => (
                                <List.Item>
                                    <Card
                                        key={item.id}
                                        title={
                                            <div style={{ display: "flex", alignItems: "center" }}>
                                                <Text ellipsis={true} style={{ maxWidth: 150 }}>
                                                    {item.name}
                                                </Text>
                                                <StayDetailInfoButton stay={item} />
                                            </div>
                                        }
                                        actions={[<ViewReservationsButton stay={item} />]}
                                        extra={<RemoveStayButton stay={item} onRemoveSuccess={this.loadData} />}
                                    >
                                        {
                                            <Carousel
                                                dots={true}
                                                arrows={true}
                                                prevArrow={<LeftCircleFilled />}
                                                nextArrow={<RightCircleFilled />}
                                            >
                                            {item.images.map((image, index) => (
                                                <div key={index}>
                                                <Image src={image.url} width="100%" />
                                                </div>
                                            ))}
                                            </Carousel>
                                        }
                                    </Card>
                                </List.Item>    
                            )}
                        />

                </Col>

                <Col span={1}></Col>
            </Row>
        );
    }
};

export default MyStays;