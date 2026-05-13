import { describe, expect, it } from 'vitest'

import { summarizeCases } from './casePresentation'
import type { CaseSummary } from './caseSummary'

describe('summarizeCases', () => {
  it('counts total, open, and in-review cases', () => {
    const cases: CaseSummary[] = [
      { id: 1, title: 'Missing documents', status: 'OPEN', attentionLevel: 'IMMEDIATE' },
      { id: 2, title: 'Payment dispute', status: 'IN_REVIEW', attentionLevel: 'FOLLOW_UP' },
      { id: 3, title: 'Policy update', status: 'CLOSED', attentionLevel: 'ARCHIVE' },
    ]

    expect(summarizeCases(cases)).toEqual({
      total: 3,
      open: 1,
      inReview: 1,
    })
  })
})