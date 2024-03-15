import React from 'react'
import { Property } from 'csstype'

interface SpacerProps {
  mt?: Property.MarginTop
  mr?: Property.MarginRight
  mb?: Property.MarginBottom
  ml?: Property.MarginLeft
}

const Spacer: React.FC<SpacerProps> = ({ mt, mr, mb, ml }) => {
  return <div style={{
    marginTop: mt,
    marginRight: mr,
    marginBottom: mb,
    marginLeft: ml
  }} />
}

export default Spacer