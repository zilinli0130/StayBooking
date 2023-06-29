/**
 * Copyright (c) 2023
 *
 * @summary Implementation of HostHomePage.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// Antd imports
import { Tabs } from "antd";

// React imports
import React from "react";

// Project imports
import UploadStay from "./UploadStay";
import MyStays from "./MyStays";

const { TabPane } = Tabs;
  
class HostHomePage extends React.Component {

    state = {
        key: "1",
    }

    onDefaultKey = () => {
        this.setState({
            key: "1",
        });
    }

    render() {
        return (
            <>
                <Tabs 
                    defaultActiveKey="1" 
                    activeKey={this.state.key}
                    destroyInactiveTabPane={true}
                    onChange={(newKey) => {this.setState({key: newKey})}}
                >
                    <TabPane 
                        tab="My Stays" 
                        key="1"
                    >
                        <MyStays />
                    </TabPane>
                    <TabPane 
                        tab="Upload Stay" 
                        key="2"    
                    >
                        <UploadStay onDefaultKey={this.onDefaultKey}/>
                    </TabPane>
                </Tabs>
            </>
        );
    }

};

export default HostHomePage;